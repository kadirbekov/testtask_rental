package com.dkadirbekov.factory;

import com.dkadirbekov.calculation.PriceCalculation;
import com.dkadirbekov.calculation.impl.NewReleasesPriceCalculation;
import com.dkadirbekov.calculation.impl.OldFilmsPriceCalculation;
import com.dkadirbekov.calculation.impl.RegularFilmsPriceCalculation;
import com.dkadirbekov.model.FilmType;
import com.dkadirbekov.model.FilmType.FilmTypeCode;

/**
 * Factory class for {@link PriceCalculation}
 */
public class PriceCalculationFactory {

    private static volatile PriceCalculationFactory instance;

    private PriceCalculationFactory() {
    }

    public static PriceCalculationFactory getInstance() {
        if (instance == null) {
            synchronized (PriceCalculationFactory.class) {
                if (instance == null) {
                    instance = new PriceCalculationFactory();
                }
            }
        }
        return instance;
    }

    /**
     * @param filmTypeCode {@link FilmType#getCode()}
     * @return new instance of realization of {@link PriceCalculation}
     */
    public PriceCalculation newInstance(String filmTypeCode) {
        if (filmTypeCode.equals(FilmTypeCode.NEW_RELEASES.name())) {
            return new NewReleasesPriceCalculation();
        } else if (filmTypeCode.equals(FilmTypeCode.REGULAR_FILMS.name())) {
            return new RegularFilmsPriceCalculation();
        } else if (filmTypeCode.equals(FilmTypeCode.OLD_FILMS.name())) {
            return new OldFilmsPriceCalculation();
        } else {
            throw new UnsupportedOperationException("not supported yet type " + filmTypeCode);
        }
    }
}
