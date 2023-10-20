package com.pluralsight;
import java.util.*;
import java.io.*;
    public class OnlineShop {
        //This is a coding rats nest i know, I was focused on doing as much as i could in the time available, b merciful
        private static Map<String, Product> inventory = new HashMap<>();
        private static ShoppingCart cart = new ShoppingCart();
        public static void main(String[] args) {
            Scanner keyboard = new Scanner(System.in);
            loadProductsFromCSV("src/main/resources/Products.csv");
            boolean shopMenu = true;
            boolean checkedOut = false;
            while (shopMenu) {
                System.out.println("Welcome to the shop! Ignore that it's a CLI interface, which is kinda sketchy.");
                System.out.println("1. Show all Products");
                System.out.println("2. Show your cart");
                System.out.println("3. Checkout");
                System.out.println("4. Quit");
                System.out.print("What would you like to do?: ");
                int choice = keyboard.nextInt();
                switch (choice) {
                    case 1:
                        displayProducts();
                        break;
                    case 2:
                        displayCartSubMenu();
                        break;
                    case 3:
                        System.out.println("This option is currently broken, and leads nowhere.");
                        checkoutCart();
                        shopMenu = false;
                        break;
                    case 4:
                        System.out.println("Cool, get out.");
                        shopMenu = false;
                        break;
                    default:
                        System.out.println("NO! You can't do that! That's breaking the rules!");
                }}
            if (checkedOut) {
                shopMenu = false;
            }}
        //Now for Method-Mania to get underway.
        public static void loadProductsFromCSV(String csvFile) {
            try (BufferedReader buffr = new BufferedReader(new FileReader(csvFile))) {
                String line;
                while ((line = buffr.readLine()) != null) {
                    String[] data = line.split("\\|");
                    if (data.length == 4) {
                        String id = data[0];
                        String name = data[1];
                        double price = Double.parseDouble(data[2]);
                        String department = data[3];
                        Product product = new Product(id, name, price, department);
                        inventory.put(id, product);
                    }}}
            catch (IOException e) {
                e.printStackTrace();
            }}
        public static void displayProducts() {
            System.out.println("List of what's in stock: ");
            for (Product product : inventory.values()) {
                System.out.println(product.getId() + " | " + product.getName() + " | $" + product.getPrice() + " | " + product.getDepartment());
            }}
        public static void displayCartSubMenu() {
            Scanner keyboard = new Scanner(System.in);
            while (true) {
                System.out.println("Cart Options:");
                System.out.println("1. Show your cart");
                System.out.println("2. Add to cart");
                System.out.println("3. Remove from cart");
                System.out.println("4. Quit to main menu");
                System.out.println("5. Check Out");
                System.out.print("What would you like to do?: ");
                int cartSelect = keyboard.nextInt();
                switch (cartSelect) {
                    case 1:
                        displayCart();
                        break;
                    case 2:
                        addProductToCart();
                        break;
                    case 3:
                        removeProductFromCart();
                        break;
                    case 4:
                        checkoutCart();
                        return;
                    case 5:
                        return;
                    default:
                        System.out.println("no. knock it off.");
                }}}
        public static void displayCart() {
            List<Product> cartItems = cart.getItems();
            Scanner scanner = new Scanner(System.in);
            if (cartItems.isEmpty()) {
                System.out.println("Your cart got caught mad lacking: ");
                scanner.nextLine();
            } else {
                System.out.println("This your cart right now: ");
                for (Product product : cartItems) {
                    System.out.println(product.getId() + " | " + product.getName() + " | $" + product.getPrice() + " | " + product.getDepartment());
                }
                System.out.println("Total Price: $" + cart.getTotalPrice());
            }}
//NOW BEGINS THE ADDITIONS
        public static void addProductToCart() {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("How would you like to add a product to your cart?");
            System.out.println("1. By ID");
            System.out.println("2. By Name");
            System.out.println("3. By Department");
            System.out.println("4. Quit");
            System.out.print("Whaddya wanna do?: ");
            int choice = keyboard.nextInt();
            keyboard.nextLine();

            switch (choice) {
                case 1:
                    addProductByID(keyboard);
                    break;
                case 2:
                    addProductByName(keyboard);
                    break;
                case 3:
                    addProductByDepartment(keyboard);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Whoa there slow your roll, Jimbo. You weren't supposed to see this line.");
                    System.out.println("You are now banned from the Mickey Mouse Club.");
            }}
        public static void addProductByID(Scanner keyboard) {
            System.out.print("Enter the ID of the product you want to add to the cart: ");
            String productId = keyboard.nextLine();
            Product product = inventory.get(productId);

            if (product != null) {
                cart.addItem(product);
                System.out.println(product.getName() + " added to the cart.");
            } else {
                System.out.println("That doesn't even actually exist. Why are you lying?");
            }}
        public static void addProductByName(Scanner keyboard) {
            System.out.print("What's the name of what you want? ");
            String productName = keyboard.nextLine();
            for (Product product : inventory.values()) {
                if (product.getName().equalsIgnoreCase(productName)) {
                    cart.addItem(product);
                    System.out.println(product.getName() + " added to the cart.");
                    return;
                }}
            System.out.println("Nope, not here, sowwy.");
        }
        public static void displayProductsByDepartment(String department) {
            System.out.println("Products in the " + department + " department:");
            for (Product product : inventory.values()) {
                if (product.getDepartment().equalsIgnoreCase(department)) {
                    System.out.println(product.getId() + " | " + product.getName() + " | $" + product.getPrice());
                }}}
//WIP, COMING SOON!
        public static void addProductByDepartment(Scanner keyboard) {
            System.out.println("Choose a department: ");
            Set<String> uniqueDepartments = new HashSet<>();
            for (Product product : inventory.values()) {
                uniqueDepartments.add(product.getDepartment());
            }
            int departmentChoice = 1;
            for (String department : uniqueDepartments) {
                System.out.println(departmentChoice + ". " + department);
                departmentChoice++;
            }
            System.out.print("Enter the department name: ");
            int selectedDepartment = keyboard.nextInt();
            keyboard.nextLine();
            if (selectedDepartment >= 1 && selectedDepartment <= uniqueDepartments.size()) {
                List<String> departmentsList = new ArrayList<>(uniqueDepartments);
                String selectedDepartmentName = departmentsList.get(selectedDepartment - 1);
                displayProductsByDepartment(selectedDepartmentName);
                System.out.print("Enter the product ID to add to the cart, \n or press 0 to return: ");
                String productID = keyboard.nextLine();
                if (!productID.equals("0")) {
                    Product product = inventory.get(productID);
                    if (product != null) {
                        cart.addItem(product);
                        System.out.println(product.getName() + " added to the cart.");
                    } else {
                        System.out.println("Nah dude that's not real, you made that up.");
                    }}
            } else {
                System.out.println("I should have you arrested for suggesting we have a department like that here.");
            }}
        //NOW BEGINS THE REMOVES!
        public static void removeProductFromCart() {
            Scanner keyboard = new Scanner(System.in);
            //More menus, Jamie.
            System.out.println("How would you like to jettison a product?");
            System.out.println("1. By ID");
            System. out.println("2. By Name");
            System.out.println("3. By Department");
            System.out.println("4. Cancel");
            System.out.print("What's your choice?: ");
            int choice = keyboard.nextInt();
            keyboard.nextLine();
            switch (choice) {
                case 1:
                    removeProductByID(keyboard);
                    break;
                case 2:
                    removeProductByName(keyboard);
                    break;
                case 3:
                    removeProductByDepartment(keyboard);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("No judgment, everyone makes mistakes. \n Just not as serious as yours are, when you put the wrong input here!.");
            }}
        public static void removeProductByID(Scanner keyboard) {
            System.out.print("Input the ID: ");
            String productId = keyboard.nextLine();
            Product productToRemove = null;
            for (Product product : cart.getItems()) {
                if (product.getId().equalsIgnoreCase(productId)) {
                    productToRemove = product;
                    break;
                }}
            if (productToRemove != null) {
                cart.removeItem(productToRemove);
                System.out.println(productToRemove.getName() + " has been removed from your cart.");
            } else {
                System.out.println("Nope, that's not here anyways.");
            }}
        public static void removeProductByName(Scanner keyboard) {
            System.out.print("Input the name: ");
            String productName = keyboard.nextLine();
            Product productToRemove = null;
            for (Product product : cart.getItems()) {
                if (product.getName().equalsIgnoreCase(productName)) {
                    productToRemove = product;
                    break;
                }}
            if (productToRemove != null) {
                cart.removeItem(productToRemove);
                System.out.println(productToRemove.getName() + " has been removed from your cart.");
            } else {
                System.out.println("That's not even here right now, what are you talking about?");
            }}
        public static void removeProductByDepartment(Scanner keyboard) {
            System.out.println("Input the department you want to purge from your cart: ");
            Set<String> uniqueDepartmentsInCart = new HashSet<>();
            for (Product product : cart.getItems()) {
                uniqueDepartmentsInCart.add(product.getDepartment());
            }
            int departmentChoice = 1;
            for (String department : uniqueDepartmentsInCart) {
                System.out.println(departmentChoice + ". " + department);
                departmentChoice++;
            }
            System.out.print("Input the department name: ");
            int selectedDepartment = keyboard.nextInt();
            keyboard.nextLine();
            if (selectedDepartment >= 1 && selectedDepartment <= uniqueDepartmentsInCart.size()) {
                List<String> departmentsList = new ArrayList<>(uniqueDepartmentsInCart);
                String selectedDepartmentName = departmentsList.get(selectedDepartment - 1);
                displayProductsInCartByDepartment(selectedDepartmentName);
                System.out.print("Enter the product ID to remove from the cart, \n or press 0 to return: ");
                String productID = keyboard.nextLine();
                if (!productID.equals("0")) {
                    Product productToRemove = findProductInCart(productID, selectedDepartmentName);
                    if (productToRemove != null) {
                        cart.removeItem(productToRemove);
                        System.out.println(productToRemove.getName() + " removed from the cart.");
                    } else {
                        System.out.println("Please stop just inventing fake things to remove.");
                    }}} else {
                System.out.println("That's a totally valid input! \n Jk, it's not, & you should be ashamed of yourself.");
            }}
        public static void displayProductsInCartByDepartment(String department) {
            System.out.println("Products in the " + department + " department in your cart:");
            for (Product product : cart.getItems()) {
                if (product.getDepartment().equalsIgnoreCase(department)) {
                    System.out.println(product.getId() + " | " + product.getName() + " | $" + product.getPrice());
                }}}
        public static Product findProductInCart(String productID, String department) {
            for (Product product : cart.getItems()) {
                if (product.getId().equals(productID) && product.getDepartment().equalsIgnoreCase(department)) {
                    return product;
                }}return null;}
        //THIS IS A WIP! WILL RETURN TO THIS!!!!
        public static void checkoutCart() {
            List<Product> checkoutItems = cart.getCheckoutItems();
            double totalPrice = 0.0;
            System.out.println("Here's your receipt: ");
            System.out.println("I'm contractually obligated to wish you a good day today.");
        }}