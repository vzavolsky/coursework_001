import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Employee[] employees = new Employee[10];
        Scanner s = new Scanner(System.in);
        String command = "";
        boolean listCreated = false;
        do {
            System.out.print("Введите команду (? - список команд): ");
            command = s.nextLine();
            switch (command) {
                case "?": {
                    System.out.println(
                            "Список команд:\n" +
                            "create - создать список сотрудников случайным образом;\n" +
                            "show - вывести список сотрудников;\n" +
                            "delete - удалить сотудника (ввод ID);\n" +
                            "add - добавить сотрудника;\n" +
                            "index - индексация зарплаты сотрудников (ввод % инднксации);\n" +
                            "max - максимальная уровень ЗП;\n" +
                            "min - минимальный уровень ЗП;\n" +
                            "average - средняя ЗП;\n" +
                            "exit - завершение программы.");
                    break;
                }
                case "exit": {
                    System.out.println("Выход.");
                    break;
                }
                case "create": {
                    if (!listCreated) {
                        employees = creatEmployeesList(10);
                        listCreated = true;
                        System.out.println("Список сотрудников создан.");
                    } else {
                        System.out.println("Список сотрудников полон.");
                    }
                    break;
                }
                case "show": {
                    if (listCreated) {
                        showEmployeesList(employees);
                    } else {
                        System.out.println("Список сотрудников пуст.");
                    }
                    break;
                }
                case "delete": {
                    employees = deleteEmployee(employees);
                    break;
                }
                case "add": {
                    employees = addEmployee(employees);
                    break;
                }
                case "index": {
                    employees = indexSalary(employees);
                    break;
                }
                case "max": {
                    System.out.println("Максимальная зарплата сотрудника: " + employees[maxSalaryIndex(employees)]);
                    break;
                }
                case "min": {
                    System.out.println("Минимальная зарплата сотрудника: " + employees[minSalaryIndex(employees)]);
                    break;
                }
                case "average": {
                    System.out.println("Средняя зарплата: " + averageSalary(employees));
                    break;
                }
                default: {
                    System.out.printf("Команды %s не существует. Список команд - \"?\".\n",command);
                }
            }
        } while (!command.equals("exit"));
    }

    private static void showEmployeesList(Employee[] employees) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                System.out.printf("%-4d %-30s %-10d %-10s \n",employees[i].getEmployeeID(), employees[i], employees[i].getSalary(), employees[i].getDepartment());
            }
        }
    }

    public static Employee[] creatEmployeesList(int numberOfEmployees) {
        String[] names = {"John", "Sarah", "Mike", "Bob", "Robert", "Donna", "Anna", "Lisa", "George", "Peter", "Denny"};
        String[] fNames = {"Melory", "Gray", "Berg", "Davis", "Wild", "Shield", "Chain", "Chan"};
        String[] deparments = {"Sale Department", "Top Management Department", "Storage Department", "Manufactory Department"};
        int index;
        Random s = new Random();
        Employee employee[] = new Employee[numberOfEmployees];
        for (int i = 0; i < numberOfEmployees; i++) {
            employee[i] = new Employee();
            index = s.nextInt(names.length - 1);
            employee[i].setName(names[index]);
            index = s.nextInt(fNames.length - 1);
            employee[i].setFamilyName(fNames[index]);
            index = s.nextInt(deparments.length - 1);
            employee[i].setDepartment(deparments[index]);
            employee[i].setSalary(s.nextInt(100_000) + 50_000);
        }
        return employee;
    }
    public static Employee[] deleteEmployee(Employee[] employees) {
        int deleteID;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID сотрудника для удаления: ");
        deleteID = scanner.nextInt();
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                if (employees[i].getEmployeeID() == deleteID) {
                    employees[i] = null;
                }
            }
        }
        employees = nullsToTheEnd(employees);
        return employees;
    }
    public static Employee[] addEmployee(Employee[] employees) {
        boolean employeeAdded = false;
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null) {
                employees[i] = new Employee();
                System.out.print("Имя: ");
                employees[i].setName(scanner.next());
                System.out.print("Фамилия: ");
                employees[i].setFamilyName(scanner.next());
                System.out.print("Зарплата: ");
                employees[i].setSalary(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Департамент: ");
                employees[i].setDepartment(scanner.nextLine());
                System.out.println();
                employeeAdded = true;
                break;
            }
        }
        if (employeeAdded) {
            System.out.println("Сотрудник успешно добавлен.");
        } else {
            System.out.println("Сотрудника добавить нет возможности. Список полон.");
        }
        employees = nullsToTheEnd(employees);
        return employees;
    }
    public static Employee[] nullsToTheEnd(Employee[] employees) {
        Employee[] sortedEmployees = new Employee[employees.length];
        int j = 0;
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                sortedEmployees[j] = employees[i];
                j++;
            }
        }
        return sortedEmployees;
    }
    public static Employee[] indexSalary(Employee[] employees) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите % индексации зарплаты: ");
        int salatyIndex = (int) scanner.nextInt();
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                employees[i].setSalary(employees[i].getSalary() + (int) (employees[i].getSalary() / 100 * salatyIndex));
            }
        }
        return employees;
    }
    public static int maxSalaryIndex(Employee[] employees) {
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                if (employees[i].getSalary() > max) {
                    max = employees[i].getSalary();
                    maxIndex = i;
                }
            }
        }
        return maxIndex;
    }
    public static int minSalaryIndex(Employee[] employees) {
        int max = 0;
        int minIndex = 0;
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                if (employees[i].getSalary() < max) {
                    max = employees[i].getSalary();
                    minIndex = i;
                }
            }
        }
        return minIndex;
    }
    public static int averageSalary(Employee[] employees) {
        int averageSalary = 0;
        int counteEmpoyees = 0;
        int sumSalary = 0;
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                sumSalary += employees[i].getSalary();
                counteEmpoyees++;
            }
        }
        return (int) (sumSalary / counteEmpoyees);
    }
}