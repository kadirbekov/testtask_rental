package com.dkadirbekov.service.impl;

import com.dkadirbekov.dao.CustomerDao;
import com.dkadirbekov.dao.FilmDao;
import com.dkadirbekov.dao.FilmTypeDao;
import com.dkadirbekov.dao.RentalDao;
import com.dkadirbekov.dao.impl.CustomerDaoMemoryImpl;
import com.dkadirbekov.dao.impl.FilmDaoMemoryImpl;
import com.dkadirbekov.dao.impl.FilmTypeDaoMemoryImpl;
import com.dkadirbekov.dao.impl.RentalDaoMemoryImpl;
import com.dkadirbekov.model.*;
import com.dkadirbekov.model.Film.Status;
import com.dkadirbekov.model.FilmType.FilmTypeCode;
import com.dkadirbekov.model.Menu.State;
import com.dkadirbekov.service.MenuService;
import com.dkadirbekov.service.RentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * {@inheritDoc}
 * implementation with console
 */
public class MenuServiceImpl implements MenuService {

    private static volatile MenuServiceImpl instance;

    private CustomerDao customerDao;
    private FilmDao filmDao;
    private RentService rentService;
    private FilmTypeDao filmTypeDao;
    private RentalDao rentalDao;
    private volatile Menu menu;
    private Scanner scanner;

    private MenuServiceImpl() {
        customerDao = CustomerDaoMemoryImpl.getInstance();
        filmDao = FilmDaoMemoryImpl.getInstance();
        rentService = RentServiceImpl.getInstance();
        filmTypeDao = FilmTypeDaoMemoryImpl.getInstance();
        rentalDao = RentalDaoMemoryImpl.getInstance();

        menu = Menu.getInstance();
        scanner = new Scanner(System.in);
    }

    public static MenuServiceImpl getInstance() {
        if (instance == null) {
            synchronized (MenuServiceImpl.class) {
                if (instance == null) {
                    instance = new MenuServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void drawMenu() {
        if (menu.getState().equals(State.CHOOSE_REGISTER_OR_SIGN_IN)) {
            System.out.println("Have you been registered?");
            System.out.println("Y/N?");
        } else if (menu.getState().equals(State.REGISTER)) {
            System.out.println("Registration!");
            System.out.println("Enter your code(login), will be used for signing in:");
            System.out.print("Code: ");
        } else if (menu.getState().equals(State.SIGN_IN)) {
            System.out.println("Enter your code(login), for signing in:");
            System.out.println("Or type `register` to register");
            System.out.println("Or type `exit` to exit");
            System.out.print("Code: ");
        } else if (menu.getState().equals(State.CHOOSE_FILM)) {
            List<Film> availableFilms = filmDao.getAllAvailable();
            if (availableFilms.isEmpty()) {
                System.out.println("No available films! Sorry!");
            }
            System.out.println("Choose film! Enter id of film, that you want to rent");
            availableFilms.forEach(film -> System.out.println(film.toString()));
            System.out.println("Enter id of film if you want to rent");
            System.out.println("Enter `return` to return film");
            System.out.println("Enter `exit` to exit");
        } else if (menu.getState().equals(State.ENTER_DAYS)) {
            System.out.println("You have choosed film: " + menu.getFilm().toString());
            System.out.println("For how many days you want this film to rent");
        } else if (menu.getState().equals(State.CONFIRM_RENTAL)) {
            System.out.println("Confirm rental:");
            System.out.println(String.format("%s %d days %s", menu.getFilm().toString(), menu.getDaysForRental(), menu.getPrice().toString()));
            System.out.println("Y/N?");
        } else if (menu.getState().equals(State.ADMIN_MENU)) {
            System.out.println("Choose action:");
            System.out.println("1. Add a film");
            System.out.println("2. Remove a film");
            System.out.println("3. Change the type of a film");
            System.out.println("4. List all films");
            System.out.println("5. List all available films");
            System.out.println("6. Active rentals for a certain customer");
            System.out.println("7. Active rentals for all customers");
            System.out.println("8. Sign out");
            System.out.println("0. Exit");
        } else if (menu.getState().equals(State.ADD_FILM)) {
            System.out.println("Enter the name of the film");
        } else if (menu.getState().equals(State.REMOVE_FILM)) {
            System.out.println("Here is available films. Enter id of film to remove:");
            filmDao.getAllAvailable().forEach(film -> System.out.println(film.toString()));
        } else if (menu.getState().equals(State.CHANGE_TYPE_OF_FILM)) {
            System.out.println("Here is all films. Enter id of film to change type:");
            filmDao.getAll().forEach(film -> System.out.println(film.toString()));
        } else if (menu.getState().equals(State.LIST_ALL_FILMS)) {
            System.out.println("List of films:");
            filmDao.getAll().forEach(film -> System.out.println(film.toString()));
            menu.setState(State.ADMIN_MENU);
        } else if (menu.getState().equals(State.LIST_ALL_AVAILABLE_FILMS)) {
            System.out.println("List of available films:");
            filmDao.getAllAvailable().forEach(film -> System.out.println(film.toString()));
            menu.setState(State.ADMIN_MENU);
        } else if (menu.getState().equals(State.RENTALS_BY_CUSTOMER)) {
            System.out.println("Enter id of customer:");
            List<Customer> customers = customerDao.getAll();
            customers.forEach(customer -> System.out.println(customer.toString()));
        } else if (menu.getState().equals(State.ALL_RENTALS)) {
            List<Rental> rentals = rentalDao.listByStatus(Rental.RentalStatus.ACTIVE);
            if (rentals.isEmpty()) {
                System.out.println("No active rentals");
                return;
            }

            System.out.println("Films, that are in rent");
            rentals.forEach(rental -> System.out.println(rental.toString()));

            BigDecimal totalAmount = rentals.stream().filter(rental -> !rental.getPrice().isPaidWithBonusPoints())
                    .map(rental -> rental.getPrice().getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
                return;
            }
            String currency = rentals.stream().filter(rental -> !rental.getPrice().isPaidWithBonusPoints()).findFirst()
                    .map(rental -> rental.getPrice().getCurrencyCode()).orElse(null);
            System.out.println(String.format("Total price: %s %s", totalAmount.toString(), currency));
            menu.setState(State.ADMIN_MENU);
        } else if (menu.getState().equals(State.RETURN_FILM)) {
            List<Rental> activeRentals = rentalDao.getActiveByCustomer(menu.getCurrentCustomer().getId());
            if (activeRentals.isEmpty()) {
                System.out.println("There is no active rentals");
                menu.setState(State.CHOOSE_FILM);
                return;
            }
            System.out.println("Enter id of film, that you want to return");
            activeRentals.forEach(rental -> System.out.println(rental.toString()));
        }
    }

    public void inputValue() {
        String input = scanner.nextLine();
        if (menu.getState().equals(State.CHOOSE_REGISTER_OR_SIGN_IN)) {
            if (input.equalsIgnoreCase("y")) {
                menu.setState(State.SIGN_IN);
            } else {
                menu.setState(State.REGISTER);
            }
        } else if (menu.getState().equals(State.REGISTER)) {
            customerDao.add(new Customer(input));
            menu.setState(State.SIGN_IN);
            System.out.println("Congratulations! You have been registered. Your code(login) is " + input);
        } else if (menu.getState().equals(State.SIGN_IN)) {
            if (input.equalsIgnoreCase("register")) {
                menu.setState(State.REGISTER);
                return;
            } else if (input.equalsIgnoreCase("exit")) {
                menu.setState(State.EXIT);
                return;

            }
            Customer customer = customerDao.getByCode(input);
            if (customer == null) {
                System.out.println("Customer not found!");
                return;
            }
            menu.setCurrentCustomer(customer);
            if (customer.getCode().equals("admin")) {
                System.out.println("Congratulations! You have been signed in as admin.");
                menu.setState(State.ADMIN_MENU);
                return;
            }
            menu.setState(State.CHOOSE_FILM);
            System.out.println("Congratulations! You have been signed in.");
        } else if (menu.getState().equals(State.CHOOSE_FILM)) {
            if (input.equalsIgnoreCase("exit")) {
                menu.setCurrentCustomer(null);
                menu.setState(State.CHOOSE_REGISTER_OR_SIGN_IN);
                return;
            }

            if (input.equalsIgnoreCase("return")) {
                menu.setState(State.RETURN_FILM);
                return;
            }

            try {
                Long idOfFilm = Long.valueOf(input);
                Film film = filmDao.getById(idOfFilm);
                if (!film.getStatus().equals(Status.AVAILABLE)) {
                    System.out.println("Film is not available");
                    System.out.println("Please choose another film");
                    menu.setState(State.CHOOSE_FILM);
                    return;
                }
                menu.setFilm(film);
                menu.setState(State.ENTER_DAYS);
            } catch (Exception ex) {
                System.out.println("Enter right value of id of film");
            }
        } else if (menu.getState().equals(State.ENTER_DAYS)) {
            try {
                Integer days = Integer.valueOf(input);
                Price price = rentService.calculatePrice(menu.getFilm(), days);
                menu.setPrice(price);
                menu.setDaysForRental(days);
                menu.setState(State.CONFIRM_RENTAL);
            } catch (Exception ex) {
                System.out.println("Enter right value for days");
            }
        } else if (menu.getState().equals(State.CONFIRM_RENTAL)) {
            if (input.equalsIgnoreCase("y")) {
                Customer customer = menu.getCurrentCustomer();

                Rental rental = new Rental();
                rental.setFilm(menu.getFilm());
                rental.setDays(menu.getDaysForRental());
                rental.setCustomer(customer);
                rental.getFilm().setStatus(Status.RENTED);

                if (customer.getBonusPoints() >= 25) {
                    rental.setPrice(new Price(25));
                    customer.setBonusPoints(customer.getBonusPoints() - 25);
                    System.out.println(String.format("%s %d days (Paid with 25 bonus points)", menu.getFilm().toString(), menu.getDaysForRental()));
                } else {
                    rental.setPrice(menu.getPrice());
                    System.out.println(String.format("%s %d days (Paid with %s)", menu.getFilm().toString(), menu.getDaysForRental(), menu.getPrice().toString()));
                }

                if (rental.getFilm().getType().getCode().equals(FilmTypeCode.NEW_RELEASES.name())) {
                    customer.setBonusPoints(customer.getBonusPoints() + 2);
                } else {
                    customer.setBonusPoints(customer.getBonusPoints() + 1);
                }
                System.out.println(String.format("Remaining bonus points: %d", customer.getBonusPoints()));

                rentalDao.add(rental);

                menu.setFilm(null);
                menu.setPrice(null);
                menu.setDaysForRental(null);
                menu.setState(State.CHOOSE_FILM);
            } else {
                menu.setState(State.CHOOSE_FILM);
            }
        } else if (menu.getState().equals(State.ADMIN_MENU)) {
            switch (input) {
                case "1":
                    menu.setState(State.ADD_FILM);
                    break;
                case "2":
                    menu.setState(State.REMOVE_FILM);
                    break;
                case "3":
                    menu.setState(State.CHANGE_TYPE_OF_FILM);
                    break;
                case "4":
                    menu.setState(State.LIST_ALL_FILMS);
                    break;
                case "5":
                    menu.setState(State.LIST_ALL_AVAILABLE_FILMS);
                    break;
                case "6":
                    menu.setState(State.RENTALS_BY_CUSTOMER);
                    break;
                case "7":
                    menu.setState(State.ALL_RENTALS);
                    break;
                case "8":
                    menu.setState(State.CHOOSE_REGISTER_OR_SIGN_IN);
                    break;
                case "0":
                    menu.setState(State.EXIT);
                    break;
            }
        } else if (menu.getState().equals(State.ADD_FILM)) {
            System.out.println("Enter description for the film");
            String description = scanner.nextLine();
            List<FilmType> types = filmTypeDao.getAll();
            types.forEach(filmType -> System.out.println(filmType.toString()));
            System.out.println("Choose type of the film. Enter id of type:");
            long typeId = scanner.nextLong();
            FilmType type = filmTypeDao.getById(typeId);
            Film film = new Film();
            film.setType(type);
            film.setName(input);
            film.setDescription(description);
            filmDao.add(film);
            System.out.println("Film successfully added");
            menu.setState(State.ADMIN_MENU);
        } else if (menu.getState().equals(State.REMOVE_FILM)) {
            try {
                Long idOfFilm = Long.valueOf(input);
                Film film = filmDao.getById(idOfFilm);
                if (film == null || !film.getStatus().equals(Status.AVAILABLE)) {
                    System.out.println("Film is not available");
                    return;
                }
                film.setStatus(Status.ARCHIVED);
                System.out.println("Film successfully removed");
            } catch (Exception ex) {
                System.out.println("Enter the right value of id");
            }
        } else if (menu.getState().equals(State.CHANGE_TYPE_OF_FILM)) {
            try {
                Long idOfFilm = Long.valueOf(input);
                Film film = filmDao.getById(idOfFilm);
                if (film == null || film.getStatus().equals(Status.ARCHIVED)) {
                    System.out.println("Film is not available");
                    return;
                }
                System.out.println("Choose type. Enter id of type:");
                filmTypeDao.getAll().forEach(filmType -> System.out.println(filmType.toString()));

                long typeId = scanner.nextLong();
                FilmType newType = filmTypeDao.getById(typeId);
                if (newType == null) {
                    System.out.println("Type not found. Enter right value of id");
                    return;
                }
                film.setType(newType);
                System.out.println("Type of the film successfully changed");
                menu.setState(State.ADMIN_MENU);
            } catch (Exception ex) {
                System.out.println("Enter the right value of id");
            }
        } else if (menu.getState().equals(State.RENTALS_BY_CUSTOMER)) {
            try {
                Long idOfCustomer = Long.valueOf(input);
                List<Rental> rentals = rentalDao.getActiveByCustomer(idOfCustomer);

                if (rentals.isEmpty()) {
                    System.out.println("No active rentals for customer");
                    return;
                }

                System.out.println("Films, that are in rent by customer");
                rentals.forEach(rental -> System.out.println(rental.toString()));
                BigDecimal totalAmount = rentals.stream().filter(rental -> !rental.getPrice().isPaidWithBonusPoints())
                        .map(rental -> rental.getPrice().getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
                    return;
                }
                String currency = rentals.stream().filter(rental -> !rental.getPrice().isPaidWithBonusPoints()).findFirst()
                        .map(rental -> rental.getPrice().getCurrencyCode()).orElse(null);
                System.out.println(String.format("Total price: %s %s", totalAmount.toString(), currency));

                scanner.nextLine();
            } catch (Exception ex) {
                System.out.println("Enter the right value of id");
            }
            menu.setState(State.ADMIN_MENU);
        } else if (menu.getState().equals(State.ALL_RENTALS)) {
        } else if (menu.getState().equals(State.RETURN_FILM)) {
            try {
                Long idOfFilm = Long.valueOf(input);
                Rental rental = rentalDao.getByFilmId(idOfFilm);
                if (rental == null || !rental.getRentalStatus().equals(Rental.RentalStatus.ACTIVE)) {
                    System.out.println("Rental not found");
                    return;
                }
                rental.setRentalStatus(Rental.RentalStatus.ARCHIVED);
                rental.getFilm().setStatus(Status.AVAILABLE);
                menu.setState(State.CHOOSE_FILM);
            } catch (Exception ex) {
                System.out.println("Enter the right value of id");
            }
        }
    }
}
