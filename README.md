# Shopping Cart Demo App

This is an example of a full-stack web application written in Java.

* Customers can browse through various products, which are sorted into categories.
* Customers can add products to their shopping cart. The number of items in the cart is visible on every page.
* Customers are able to view the items and quantities currently in their cart.
* Customers can adjust item quantities or remove items from their carts altogether on the shopping cart page via AJAX, so the page never reloads.
* Admins can "log in" (mock authentication) using cookies.
* Admins can create new categories and products, complete with image uploads.

## Tech Stack

This app uses the following technologies:
* `Spring` as a container for dependency injection
* `Spring Boot` for a batteries-included preconfigured server
* `Spring MVC` for request routing
* `Thymeleaf` for templating
* `Spring Data JPA` for interacting with a database
* `CSS3/Grid/Flexbox` for layout and aesthetics
* `JavaScript/ES6/AJAX` for a smooth user experience

## Potential Improvements and Extensions

1. Clean up CSS on /admin page
1. Allow users to place their orders, removing them from the cart
1. Allow users to view previous orders
1. Display recently purchased items as suggestions on the cart page so users can add those items from that page right before they place their orders
1. Add multiple images to represent each Product, with the ability to rotate through them on the product page

## Further Concepts to Demonstrate

1. Creating and submitting FormData in JavaScript
1. Sending data using JavaScript's `encodeURIComponent()` and template literals
1. HTML generation using template literals
