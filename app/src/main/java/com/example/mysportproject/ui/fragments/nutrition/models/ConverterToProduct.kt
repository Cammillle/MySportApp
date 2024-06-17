package com.example.mysportproject.ui.fragments.nutrition.models

import com.example.mysportproject.ui.fragments.nutrition.models.localdb.IProduct



class ConverterToProduct {


    fun convertFoodProduct(foodProduct: FoodProduct, grams: Float): Product {
        val multiplier = grams / 100
        foodProduct.run {
            return Product(
                eatenGrams = grams,
                consumedCalories = calories * multiplier,
                consumedAlcohol = alcohol?.times(multiplier),
                consumedAlpha_carotene = alpha_carotene?.times(multiplier),
                consumedAluminium = aluminium?.times(multiplier),
                consumedAntimony = antimony?.times(multiplier),
                consumedArsenic = arsenic?.times(multiplier),
                consumedAsh = ash?.times(multiplier),
                consumedBeta_carotene = beta_carotene?.times(multiplier),
                consumedBiotin_b7 = biotin_b7?.times(multiplier),
                consumedCadmium = cadmium?.times(multiplier),
                consumedCalcium = calcium?.times(multiplier),
                consumedCarbs = carbs.times(multiplier),
                consumedChloride = chloride?.times(multiplier),
                consumedCholecalciferol_d3 = cholecalciferol_d3?.times(multiplier),
                consumedChromium = chromium?.times(multiplier),
                consumedCobalamin_b12 = cobalamin_b12?.times(multiplier),
                consumedCopper = copper?.times(multiplier),
                consumedCryptoxanthin = cryptoxanthin?.times(multiplier),
                consumedEnergy = energy?.times(multiplier),
                consumedEnergy_without_dietary_fibre = energy_without_dietary_fibre?.times(
                    multiplier
                ),
                consumedErgocalciferol_d2 = ergocalciferol_d2?.times(multiplier),
                consumedFat = fats.times(multiplier),
                consumedFluoride = fluoride?.times(multiplier),
                consumedFolatenatural = folatenatural?.times(multiplier),
                consumedFolicacid = folicacid?.times(multiplier),
                consumedFructose = fructose?.times(multiplier),
                consumedGalactose = galactose?.times(multiplier),
                consumedGlucose = glucose?.times(multiplier),
                consumedIodine = iodine?.times(multiplier),
                consumedIron = iron?.times(multiplier),
                consumedLactose = lactose?.times(multiplier),
                consumedLead = lead?.times(multiplier),
                consumedMagnesium = magnesium?.times(multiplier),
                consumedMaltose = maltose?.times(multiplier),
                consumedMaltotriose = maltotriose?.times(multiplier),
                consumedManganese = manganese?.times(multiplier),
                consumedMercury = mercury?.times(multiplier),
                consumedMoisture = moisture?.times(multiplier),
                consumedMolybdenum = molybdenum?.times(multiplier),
                consumedMono_fats = mono_fats?.times(multiplier),
                consumedMono_fats_g = mono_fats_g?.times(multiplier),
                consumedNiacin_b3 = niacin_b3?.times(multiplier),
                consumedNickel = nickel?.times(multiplier),
                consumedNitrogen = nitrogen?.times(multiplier),
                consumedO_poly_fats = o_poly_fats?.times(multiplier),
                consumedO_poly_fats_g = o_poly_fats_g?.times(multiplier),
                consumedOmega = omega?.times(multiplier),
                consumedOmega_g = omega_g?.times(multiplier),
                consumedPantothenic_acid_b5 = pantothenic_acid_b5?.times(multiplier),
                consumedPhosphorus = phosphorus?.times(multiplier),
                consumedPotassium = potassium?.times(multiplier),
                consumedProtein = proteins.times(multiplier),
                consumedProvitamin_a = provitamin_a?.times(multiplier),
                consumedPyridoxine_b6 = pyridoxine_b6?.times(multiplier),
                consumedRetinol = retinol?.times(multiplier),
                consumedRiboflavin_b2 = riboflavin_b2?.times(multiplier),
                consumedSat_fats = sat_fats?.times(multiplier),
                consumedSat_fats_g = sat_fats_g?.times(multiplier),
                consumedSelenium = selenium?.times(multiplier),
                consumedSodium = sodium?.times(multiplier),
                consumedStarch = starch?.times(multiplier),
                consumedSucrose = sucrose?.times(multiplier),
                consumedSugar = sugar?.times(multiplier),
                consumedSulphur = sulphur?.times(multiplier),
                consumedT_poly_fats = t_poly_fats?.times(multiplier),
                consumedT_poly_fats_g = t_poly_fats_g?.times(multiplier),
                consumedThiamin_b1 = thiamin_b1?.times(multiplier),
                consumedTin = tin?.times(multiplier),
                consumedTotal_dietary_fiber = total_dietary_fibre?.times(multiplier),
                consumedTotalfolates = totalfolates?.times(multiplier),
                consumedDietary_folate_equivalents = dietary_folate_equivalents?.times(multiplier),
                consumedVitamin_a_retinol_equivalents = vitamin_a_retinol_equivalents?.times(
                    multiplier
                ),
                consumedVitamin_c = vitamin_c?.times(multiplier),
                consumedVitamin_e = vitamin_e?.times(multiplier),
                consumedZinc = zinc?.times(multiplier),
                consumedCobalt = cobalt?.times(multiplier),
                name = name
            )
        }

    }

    fun convertLocal(it: IProduct, grams: Float): Product {
        val multiplier = grams / 100
        return Product(
            name = it.name,
            eatenGrams = grams,
            consumedProtein = it.proteins * multiplier,
            consumedFat = it.fats * multiplier,
            consumedCarbs = it.carbs * multiplier,
            consumedCalories = it.calories * multiplier
        )
    }

}