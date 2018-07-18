# Shopping Cart Demo App

This is an example of a full-stack web application written in Java.

* Users can browse through various products, which are sorted into categories.
* Users can add products to their shopping cart.
* Users are able to view the items and quantities currently in their cart.
* Users can adjust item quantities or remove items from their carts altogether on the shopping cart page via AJAX, so the page never reloads.

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

1. Allow users to place their orders, removing them from the cart
1. Allow users to view previous orders
1. Display recently purchased items as suggestions on the cart page so users can add those items from that page right before they place their orders
1. Add multiple images to represent each Product, with the ability to rotate through them on the product page
1. Add Category and Product CRUD forms for admins (/admin)
