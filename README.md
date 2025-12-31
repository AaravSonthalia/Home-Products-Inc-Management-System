# Home Products Inc Management System

A Java Swing desktop application for managing retail operations with MySQL database backend. Features complete CRUD functionality for customers, orders, products, sales representatives, and payments with a modern, user-friendly interface.

## Overview

Built with Java Swing and MySQL, this system provides streamlined management of retail business operations. The application implements MVC architecture with service layers for database operations and presentation layers for user interaction, featuring professional UI design with input validation and real-time data updates.

## Features

- **Customer Management** - Create, view, and edit customer records with lifetime order tracking
- **Order Processing** - Create orders with automatic tax/discount calculations
- **Payment Processing** - Record payments via credit card, debit, or check
- **Sales Rep Management** - Track sales team with hierarchical relationships
- **Product Catalog** - Manage inventory with warehouse locations
- **Modern UI** - Consistent styling with input validation and hover effects

## Database Schema

Seven core tables with relational integrity:

- **tblWarehouse** - Storage locations
- **tblProduct** - Product catalog (links to Warehouse)
- **tblSalesRep** - Sales team (self-referential for managers)
- **tblCustomer** - Customer records (links to SalesRep)
- **tblOrder** - Order headers (links to Customer)
- **tblPayment** - Payment records (links to Customer and Order)
- **tblProductsOrder** - Order line items (many-to-many between Order and Product)

## Usage

Launch `HomeProductsIncRunner.java` to open the main menu with navigation options for all features.

### Key Workflows

**Customer Management**
- Select "View Customers" to browse/search records
- Use "Edit/New Customer" to create or modify customer data
- System automatically calculates lifetime order totals and remaining credit

**Order Processing**
- Select "New Order" and enter customer ID
- Add products by ID and quantity
- Click "Calculate Total" for automatic tax/discount (10% discount on orders >$100)
- Save to finalize the order

**Payment Recording**
- Select "New Payment" and enter customer/order IDs
- Choose payment method (credit card, debit, or check)
- System validates dates and card information

**Sales Rep & Product Management**
- Use respective menu options to create/edit records
- All forms include input validation and error handling

## Validation Rules

- **Email**: Must match format username@domain.com
- **Phone Numbers**: Exactly 10 digits
- **ZIP Codes**: 5 digits or 5+4 format
- **Sales Tax**: 0 to 0.1 (0% to 10%)
- **Commission**: 0.05 to 0.1 (5% to 10%)
- **Dates**: Order/payment dates must be current; shipping/card expiration cannot be past
- **IDs**: All referenced IDs must exist in database
- **Amounts**: Positive values only for prices, credit, and payments

## Technical Details

**Architecture**: MVC pattern with clear separation of concerns
- Models represent database entities
- Services handle business logic and database operations  
- Presentations provide GUI using Java Swing

**Color Scheme**: Professional blue-grey theme
- Primary: `#2980B9` | Secondary: `#ECF0F1` | Accent: `#3498DB`
- Header: `#34495E` | Background: `#4B6584` | Text: `#2C3E50`

**Key Features**:
- Consistent UI styling across all forms
- Comprehensive input validation with user-friendly error messages
- Automatic calculations for order totals and customer credit
- Hierarchical sales rep management with manager relationships

## Troubleshooting

**Connection Error**
- Verify credentials in `DatabaseConfig.java`
- Ensure MySQL server is running
- Check database exists

**Foreign Key Error**
- Ensure referenced records exist first
- Load data in order: Warehouse → SalesRep → Customer → Order → Payment

**Validation Errors**
- Fill all required fields (marked with *)
- Check data formats match validation rules
- Verify referenced IDs exist in database

## Authors

**Aarav S and Ethan C**  
Version 1.0.0  
Contact: aaravsonthalia@gmail.com, echan2025@pingry.org

---

*This project demonstrates a complete database-driven desktop application using Java Swing and MySQL with modern UI design and comprehensive business logic.*
