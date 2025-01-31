# ⚠️ UMWIA2004 - Java: Must Read ⚠️

WIA2004 is offered by the Faculty of Computer Science and Information Technology (FCSIT) at Universiti Malaya (UM) in Malaysia. This repository contains solutions for all labs (Lab 1 - Lab 9) implemented in Java.

To get started, feel free to browse through the solutions and use them to supplement your learning. Hopefully, you find this repository helpful and informative. If you have any questions or suggestions, please do not hesitate to reach out to me.

![last_update_shield](https://img.shields.io/badge/Last%20Update-31_January%202025-orange)

## ✨ **Project Overview**
- The repository is developed using **IntelliJ IDEA**. To set it up:
    - Open the project in IntelliJ.
    - Run `mvn clean install` to download all dependencies.
    - Once set up, you can run any code as needed.

- The entry point for most labs is **Main.java**, except for **lab9b** and **lab9c**, which consist of single files.

## ✨ **Key Features**
1. **Utility Packages**  
   The `util` package includes several utility classes to streamline code and enhance functionality:
    - **TableUtil**: Used extensively to present data and status in a tabular format. The primary method is `TableUtil.renderTable(String[] headers, String[][] rows)`, which is solely for printing purposes.
    - **Asserts**: Simplifies `if() { throw ... }` operations, making the code cleaner.
    - **LogUtil**: Formats console output for better readability.
    - **ReflectionUtils**: Provides additional functionality for reflection operations.

2. **Lab Implementations**  
   Each lab may have multiple implementations, typically labeled as:
    - **labXa, labXb, labXc**: Variants with _decreasing_ complexity.
    - **Combined version**: A complete, well-engineered implementation that simulates real-world scenarios. It adheres to the **SOLID principles** and incorporates design patterns. While it may include unused methods or variables, it is designed for robustness and scalability.
    - **labX version**: A minimalistic version with just enough code to run via **Main.java**. This is ideal for those who do not require the full complexity of the combined version.

3. **Documentation**
    - **Lab6 to Lab8**: These labs involve complex algorithms and are thoroughly documented.
    - **Other labs**: The code is straightforward, with variable names clearly conveying their intent. As a result, additional comments are minimal.

## ✨ **Notes for Users**
- If you encounter any issues (e.g., changes in questions, runtime errors, or incorrect solutions), feel free to reach out to me.
- This project offers flexibility for users to explore varying levels of complexity. Whether you're looking for a minimal implementation or a fully engineered solution, **umwia2004-java** has you covered.
