(function() {

  let cartItems = [];

  getItems();
  
  function getItems() {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        cartItems = JSON.parse(xhr.responseText);
        render();
      }
    };
    xhr.open('GET', '/cart/items');
    xhr.send();
  }

  /**
   * TODO: We could allow people to add last minute items
   * to the cart that they have ordered before.
   */
  function addProduct(productId, quantity) { }

  function updateItem(itemId, quantity) {

    const cartItemUpdate = JSON.stringify({
      id: itemId,
      quantity,
    });

    // Send the AJAX request to update the item in the cart
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        // When finished, update items and re-render
        cartItems = JSON.parse(xhr.responseText);
        render();
      }
    };
    xhr.open('PUT', '/cart/updateItem');
    // Set a header so the server recognizes the data we send as JSON
    // otherwise it will think we are just sending plain text
    xhr.setRequestHeader(
      'Content-Type',
      'application/json;charset=UTF-8'
    );
    xhr.send(cartItemUpdate);
  }

  function removeItem(itemId) {

    // First make sure the user REALLY wants to remove the item
    let absolutelyCertain = confirm('Really remove this item from your cart?');
    if (!absolutelyCertain) {
      return;
    }
    
    // Send the AJAX request to delete the item from the cart
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        // When finished, update items and re-render
        cartItems = JSON.parse(xhr.responseText);
        render();
      }
    };
    xhr.open('DELETE', '/cart/removeItem/' + itemId);
    xhr.send();
  }

  function render() {

    // Testing
    console.log({ cartItems });

    // Get container and clear it
    const cartDiv = document.querySelector('#cart');
    cartDiv.innerHTML = '';

    // If there are no elements, render a 'Cart is empty' message
    if (!cartItems.length) {
      const noItemsDiv = document.createElement('div');
      noItemsDiv.classList.add('cartItem');
      noItemsDiv.innerHTML = 'No items in cart.';
      cartDiv.appendChild(noItemsDiv);
    }

    // For any items in the cart, render that item
    cartItems.forEach(item => {

      // Determine URLs for item's product and category pages
      const productHref = '/product?id=' + item.product.id;
      const categoryHref = '/category?id=' + item.product.category.id;

      // Create a <div> to hold the item's info (will add to DOM below)
      const itemDiv = document.createElement('div');
      itemDiv.classList.add('cartItem');

      // Product Name: <h3> inside a <a> that leads to the product page
      const productNameA = document.createElement('a');
      productNameA.setAttribute('href', productHref);
      const productNameH3 = document.createElement('h3');
      productNameH3.innerHTML = item.product.name;
      productNameA.appendChild(productNameH3);
      itemDiv.appendChild(productNameA);

      // Category Name: <h4> inside a <a> that leads to the category page
      const productCategoryA = document.createElement('a');
      productCategoryA.setAttribute('href', categoryHref);
      const productCategoryH4 = document.createElement('h4');
      productCategoryH4.innerHTML = item.product.category.name;
      productCategoryA.appendChild(productCategoryH4);
      itemDiv.appendChild(productCategoryA);

      // Item Quantity
      const quantityDiv = document.createElement('div');
      quantityDiv.innerHTML = 'Quantity: ' + item.quantity;
      itemDiv.appendChild(quantityDiv);
      
      // 'Change Quantity' Item
      const changeQuantity = document.createElement('span');
      changeQuantity.classList.add('changeQuantity');
      changeQuantity.innerHTML = 'Change Quantity';
      changeQuantity.addEventListener('click', function() {

        // Get string input from the user
        const userInput = prompt(
          `How many ${item.product.name}s would you like instead?`
        );
        // Turn the string into a number (or `NaN` otherwise)
        const userInputNumber = +userInput;

        if (!Number.isInteger(userInputNumber)) {
          alert('You must specify a number.');
        }
        else if (userInputNumber === item.quantity) {
          alert('No changes will be made then.');
        }
        else {
          updateItem(item.id, userInputNumber);
        }

      });
      itemDiv.appendChild(changeQuantity);
      
      // 'X' Button: <button> when clicked removes item
      const itemRemoveButton = document.createElement('button');
      itemRemoveButton.innerHTML = '&times;';
      itemRemoveButton.addEventListener('click', function() {
        removeItem(item.id);
      });
      itemDiv.appendChild(itemRemoveButton);

      // Append to the DOM *LAST* so the window only has to re-draw once
      cartDiv.appendChild(itemDiv);

      // Finally, update the number of items in the cart as displayed
      // in the corner of the page (NOT the number of cartItems, but
      // the sum of each cartItem's quantity)
      const cartItemCountSpan = document.querySelector('#cartItemCount');
      let cartItemCount = 0;
      cartItems.forEach(item => cartItemCount += item.quantity);
      cartItemCountSpan.innerHTML = 'Items: ' + cartItemCount;
  
    });
  }

})();