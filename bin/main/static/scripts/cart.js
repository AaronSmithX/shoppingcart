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

  function addProduct(productId, quantity) {
    //
  }

  function updateItem(itemId, quantity) {
    //
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
        // When finished, remove item and re-render
        cartItems = cartItems.filter(item => item.id !== itemId);
        render();
      }
    };
    xhr.open('DELETE', '/cart/removeItem/' + itemId);
    xhr.send();
  }

  function render() {

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
      
      // 'X' Button: <button> when clicked removes item
      const itemRemoveButton = document.createElement('button');
      itemRemoveButton.innerHTML = '&times;';
      itemRemoveButton.addEventListener('click', function() {
        removeItem(item.id);
      });
      itemDiv.appendChild(itemRemoveButton);

      // Append to the DOM *LAST* so the window only has to re-draw once
      cartDiv.appendChild(itemDiv);
    });
  }

})();