package com.dkadirbekov;

import com.dkadirbekov.model.Menu;
import com.dkadirbekov.service.MenuService;
import com.dkadirbekov.service.impl.MenuServiceImpl;

public class Main {

    public static void main(String[] args) {
        Menu menu = Menu.getInstance();
        menu.setState(Menu.State.CHOOSE_REGISTER_OR_SIGN_IN);

        MenuService menuService = MenuServiceImpl.getInstance();
        while (!menu.getState().equals(Menu.State.EXIT)) {
            menuService.drawMenu();
            menuService.inputValue();
        }
        System.out.println("Goodbye!");
    }
}
