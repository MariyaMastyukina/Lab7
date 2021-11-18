
package Utils.DataUtils;

import java.util.*;

/**
 * Класс, проверяющий валидность вводимых команд
 */
public class ValidationUtils {
    private static List<String> list = Arrays.asList("help", "add", "check_in", "clear", "exit", "group_counting_by_population", "history", "info", "execute_script", "print_ascending", "remove_all_by_meters_above_sea_level", "remove_by_id", "remove_last", "show", "sign_in", "sort", "update");

    /**
     * Конструктор класса, принимающий имя команды и ее аргумент
     *
     * @param command
     * @param option
     */


    /**
     * Функция проверки валидности вводимой команды
     */
    public static Boolean isValidCommand(String command, String option) {
        boolean checker = false;
        for (String c : list) {
            if (c.equals(command)) checker = true;
        }
        if (!checker) {
            System.out.println("Такой команды не существует или она записана некорректно. Введите \"help\", чтобы получить список действующих команд");
            return false;
        }
        if (command.equals("remove_by_id") || command.equals("update")) {
            try {
                Long.parseLong(option);
            } catch (NumberFormatException e) {
                System.out.println("Выполнение команды невозможно. Введен некорректный id, повторите ввод команды");
                checker = false;
            }
        }
        if (command.equals("remove_all_by_meters_above_sea_level")) {
            try {
                Integer.parseInt(option);
            } catch (NumberFormatException e) {
                System.out.println("Выполнение команды невозможно. Введен некорректный meters_above_sea_level, повторите ввод команды");
                checker = false;
            }
        }
        return checker;
    }


    private static String readValidX(Scanner scanner) {
        System.out.println("Введите координату города X(число должно быть больше -375). Пример: 9000000.75");
        String x = isValidX(scanner);
        while (x.equals("-1000") || x.equals("-1001")) {
            if (x.equals("-1000"))
                System.out.println("Значение не может быть меньше или равно -375, введите координату города X еще раз");
            else
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            x = isValidX(scanner);
        }
        return x;
    }

    private static String readValidY(Scanner scanner) {
        System.out.println("Введите координату города Y(число должно быть больше -966). Пример: 8000000");
        String y = isValidY(scanner);
        while (y.equals("-1000") || y.equals("-1001")) {
            if (y.equals("-1000"))
                System.out.println("Значение не может быть меньше или равно -966, введите координату города Y еще раз");
            else
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            y = isValidY(scanner);
        }
        return y;
    }

    private static String readValidArea(Scanner scanner) {
        System.out.println("Введите площадь города(число должно быть больше 0). Пример: 900.8");
        String area = isValidArea(scanner);
        while (area.equals("-1000") || area.equals("-1001")) {
            if (area.equals("-1000"))
                System.out.println("Значение не может быть отрицательным или равным 0, введите площадь города еще раз");
            else
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            area = isValidArea(scanner);
        }
        return area;
    }

    private static String readValidPopulation(Scanner scanner) {
        System.out.println("Введите население города(число должно быть больше 0). Пример: 10000000");
        String population = isValidPopulation(scanner);
        while (population.equals("-1000") || population.equals("-1001")) {
            if (population.equals("-1000"))
                System.out.println("Значение не может быть отрицательным или равным 0, введите население города еще раз");
            else
                System.out.println("Формат введенного значения неверный, введите значение еще раз");
            population = isValidPopulation(scanner);
        }
        return population;
    }

    private static String readValidMetersAboveSeaLevel(Scanner scanner) {
        System.out.println("Введите на скольких метрах над уровнем моря находится город. Пример: -180");
        String metersAboveSeaLevel = isValidMetersAboveSeaLevel(scanner);
        while (metersAboveSeaLevel == null) {
            System.out.println("Формат введенного значения неверный, введите значение еще раз");
            metersAboveSeaLevel = isValidMetersAboveSeaLevel(scanner);
        }
        return metersAboveSeaLevel;
    }

    private static String readValidCapital(Scanner scanner) {
        System.out.println("Введите true, если город является столицей, иначе введите false");
        String capital = isValidCapital(scanner);
        while (capital == null) {
            System.out.println("Введенное значение некорректно, введите значение еще раз");
            capital = isValidCapital(scanner);
        }
        return capital;
    }

    private static String raedValidClimate(Scanner scanner) {
        System.out.println("Введите климат города. Выберете из этого: \n" + "HUMIDCONTINENTAL\n" + "SUBARCTIC\n" + "TUNDRA");
        String climate = isValidClimate(scanner);
        while (climate == null) {
            System.out.println("Введенное значение некорректно, введите климат города еще раз");
            climate = isValidClimate(scanner);
        }
        return climate;
    }

    private static String readValidGovernment(Scanner scanner) {
        System.out.println("Введите правительство города. Выберите из этого: \n" + "CORPORATOCRACY\n" + "MERITOCRACY\n" + "OLIGARCHY");
        String government = isValidGovernment(scanner);
        while (government == null) {
            System.out.println("Введенное значение некорректно, введите правительство города еще раз");
            government = isValidGovernment(scanner);
        }
        return government;
    }

    private static String readValidGovernor(Scanner scanner) {
        System.out.println("Чтение фамилии губернатора города. Пример: Собянин");
        String nameGovernor = isValidGovernor(scanner);
        while (nameGovernor == null) {
            System.out.println("Считанное имя записано некорректно. Введите имя еще раз");
            nameGovernor = isValidGovernor(scanner);
        }
        return nameGovernor;
    }

    public static List<String> readArgsCity(Scanner scanner) {
        List<String> args = new ArrayList<>();
        System.out.println("Введите название города. Пример: Москва");
        args.add(scanner.nextLine());
        args.add(ValidationUtils.readValidX(scanner));
        args.add(ValidationUtils.readValidY(scanner));
        args.add(ValidationUtils.readValidArea(scanner));
        args.add(ValidationUtils.readValidPopulation(scanner));
        args.add(ValidationUtils.readValidMetersAboveSeaLevel(scanner));
        args.add(ValidationUtils.readValidCapital(scanner));
        args.add(ValidationUtils.raedValidClimate(scanner));
        args.add(ValidationUtils.readValidGovernment(scanner));
        args.add(ValidationUtils.readValidGovernor(scanner));
        return args;
    }
    public static List<String> readFileArgs(Scanner scanner) {
        List<String> args = new ArrayList<>();
        args.add(scanner.nextLine());
        args.add(ValidationUtils.readFileX(scanner));
        args.add(ValidationUtils.readFileY(scanner));
        args.add(ValidationUtils.readFileArea(scanner));
        args.add(ValidationUtils.readFilePopulation(scanner));
        args.add(ValidationUtils.readFileMetersAboveSeaLevel(scanner));
        args.add(ValidationUtils.readFileCapital(scanner));
        args.add(ValidationUtils.raedFileClimate(scanner));
        args.add(ValidationUtils.readFileGovernment(scanner));
        args.add(ValidationUtils.readFileGovernor(scanner));
        return args;
    }

    private static String isValidX(Scanner scanner) {

        try {
            float x = Float.parseFloat(scanner.nextLine());
            if (x > -375F) {
                return Float.toString(x);
            } else {
                return "-1000";
            }
        } catch (NumberFormatException e) {
            return "-1001";
        }
    }


    private static String isValidY(Scanner scanner) {
        try {
            int y = Integer.parseInt(scanner.nextLine());
            if (y > -966) {
                return Integer.toString(y);
            } else {
                return "-1000";
            }
        } catch (NumberFormatException e) {
            return "-1001";
        }
    }


    private static String isValidArea(Scanner scanner) {
        try {
            double area = Double.parseDouble(scanner.nextLine());
            if (area > 0) {
                return Double.toString(area);
            } else {
                return "-1000";
            }
        } catch (NumberFormatException e) {
            return "-1001";
        }
    }

    private static String isValidPopulation(Scanner scanner) {
        try {
            int population = Integer.parseInt(scanner.nextLine());
            if (population > 0) {
                return Integer.toString(population);
            } else {
                return "-1000";
            }
        } catch (NumberFormatException e) {
            return "-1001";
        }
    }

    private static String isValidMetersAboveSeaLevel(Scanner scanner) {
        try {
            int metersAboveSeaLevel = Integer.parseInt(scanner.nextLine());
            return Integer.toString(metersAboveSeaLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String isValidCapital(Scanner scanner) {
        String capital;
        capital = scanner.nextLine();
        if (capital.equals("true") || capital.equals("false"))
            return capital;
        else
            return null;
    }


    private static String isValidClimate(Scanner scanner) {
        boolean checker = false;
        String clim = scanner.nextLine();
        if (clim.equalsIgnoreCase("HUMIDCONTINENTAL")) {
            checker = true;
        } else if (clim.equalsIgnoreCase("SUBARCTIC")) {
            checker = true;
        } else if (clim.equalsIgnoreCase("TUNDRA")) {
            checker = true;
        }
        return checker ? clim.toUpperCase() : null;
    }

    private static String isValidGovernment(Scanner scanner) {
        boolean checker = false;
        String gov = scanner.nextLine();
        if (gov.equalsIgnoreCase("CORPORATOCRACY")) {
            checker = true;
        } else if (gov.equalsIgnoreCase("MERITOCRACY")) {
            checker = true;
        } else if (gov.equalsIgnoreCase("OLIGARCHY")) {
            checker = true;
        }
        return checker ? gov.toUpperCase() : null;
    }

    private static String isValidGovernor(Scanner scanner) {
        String nameGovernor = scanner.nextLine();
        char[] symbols = nameGovernor.toLowerCase().toCharArray();
        String validationName = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        for (char c : symbols) {
            if (validationName.indexOf(c) == -1) {
                return null;
            }
        }
        return nameGovernor;
    }

    private static String readFileX(Scanner scanner) {
        String x = isValidX(scanner);
        if (x.equals("-1000") || x.equals("-1001")) {
            if (x.equals("-1000"))
                System.out.println("В файле указано неверное значение. Значение не может быть меньше -375, координата города Х по умолчанию устанавливается 0");
            else
                System.out.println("В файле указан неверный формат.Координата города Х по умолчанию устанавливается 0");
            x = "0.0";
        }
        return x;
    }

    private static String readFileY(Scanner scanner) {
        String y = isValidY(scanner);
        if (y.equals("-1000") || y.equals("-1001")) {
            if (y.equals("-1000"))
                System.out.println("В файле указан неверное значение. Значение не может быть меньше -966, координата города Y по умолчанию устанавливается 0");
            else
                System.out.println("В файле указан неверный формат.Координата города Y по умолчанию устанавливается 0");
            y = "0";
        }
        return y;
    }

    private static String readFileArea(Scanner scanner) {
        String area = isValidArea(scanner);
        if (area.equals("-1000") || area.equals("-1001")) {
            if (area.equals("-1000"))
                System.out.println("В файле указано неверное значение. Значение не может быть отрицательным или равным 0, площадь города по умолчанию устанавливается 1.0");
            else
                System.out.println("В файле указан неверный формат.Площадь города по умолчанию устанавливается 1.0");
            area = "1.0";
        }
        return area;
    }

    private static String readFilePopulation(Scanner scanner) {
        String population = isValidPopulation(scanner);
        if (population.equals("-1000") || population.equals("-1001")) {
            if (population.equals("-1000"))
                System.out.println("В файле указано неверное значение. Значение не может быть отрицательным или равным 0, введите население города еще раз, население города по умолчанию устанавливается 1");
            else
                System.out.println("В файле указан неверный формат. Население города по умолчанию устанавливается 1");
            population = "1";
        }
        return population;
    }

    private static String readFileMetersAboveSeaLevel(Scanner scanner) {
        String metersAboveSeaLevel = isValidMetersAboveSeaLevel(scanner);
        if (metersAboveSeaLevel == null) {
            System.out.println("В файле указан неверный формат.Уровень города над морем по умолчанию устанавливается 0");
            metersAboveSeaLevel = "0";
        }
        return metersAboveSeaLevel;
    }

    private static String readFileCapital(Scanner scanner) {
        String capital = isValidCapital(scanner);
        if (capital == null) {
            System.out.println("В файле указано неверное значение. По умолчанию город не является столицей");
            capital = "false";
        }
        return capital;
    }

    private static String raedFileClimate(Scanner scanner) {
        String climate = isValidClimate(scanner);
        if (climate == null) {
            System.out.println("В файле указано неверное значение. По умолчанию климат города HUMIDCONTINENTAL");
            climate = "HUMIDCONTINENTAL";
        }
        return climate;
    }

    private static String readFileGovernment(Scanner scanner) {
        String government = isValidGovernment(scanner);
        if (government == null) {
            System.out.println("В файле указано неверное значение. По умолчанию правительство города CORPORATOCRACY");
            government = "CORPORATOCRACY";
        }
        return government;
    }

    private static String readFileGovernor(Scanner scanner) {
        String nameGovernor = isValidGovernor(scanner);
        if (nameGovernor == null) {
            System.out.println("В файле указано неверное имя губернатора. Имя губернатора не вводится");
            nameGovernor = "";
        }
        return nameGovernor;
    }
}
