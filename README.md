# Medicine Dose Tracker

A Java-based desktop application for managing and reminding users about their medicine doses. The application provides a user-friendly interface to add, view, and schedule reminders for different medicines and efficiently.

## Features

- **User Login/Registration**: Allows users to log in or register to manage their own medicine schedule.
- **Medicine Management**: Users can add, view, and store details of their medicines, including name, dosage, and frequency.
- **Reminders**: The application provides reminders for each medicine based on the specified frequency.
- **Data Persistence**: Stores user and medicine data in a CSV file (`medicine_data.csv`), allowing the data to persist across sessions.
  
## Prerequisites

To run this application, you need to have:

- Java Development Kit (JDK) 8 or later.
- An IDE like Eclipse, IntelliJ IDEA, or NetBeans (optional, but recommended).
  
## Installation

1. **Clone the Repository**: Clone the repository to your local machine using:

    ```bash
    git clone https://github.com/your-username/medicinedosetrackerjava.git
    ```

2. **Open in IDE**: Open the cloned project in your preferred Java IDE.

3. **Build and Run**: Compile and run the `Medicinedosetrackerjava` class. 

    You can also run it from the command line:
    ```bash
    cd medicinedosetrackerjava
    javac -d . *.java
    java medicinedosetrackerjava.Medicinedosetrackerjava
    ```

## Usage

1. **Login or Register**: On the startup screen, enter a username to log in or register a new user.
2. **Add Medicine**: Fill in the medicine name, dosage, and frequency, then click "Add Medicine".
3. **Display Medicines**: Click "Display Medicines" to view all added medicines.
4. **Receive Reminders**: The application will show reminders based on the frequency set for each medicine.

## Files

- **`Medicinedosetrackerjava.java`**: Main Java file containing the application's logic.
- **`medicine_data.csv`**: Data file that stores user and medicine information.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any changes or improvements.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Author

- **Asus** - Initial work

## Acknowledgments

- Java Swing for providing a platform to build a GUI-based desktop application.
- Open-source communities for continuous support and improvements.

