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
    //
  }

  function render() {

    console.log({ cartItems });

    const cartDiv = document.querySelector('#cart');

    cartItems.forEach(item => {

      const productHref = '/product?id=' + item.product.id;
      const categoryHref = '/category?id=' + item.product.category.id;

      const itemDiv = document.createElement('div');
      itemDiv.classList.add('cartItem');

      const productNameA = document.createElement('a');
      productNameA.setAttribute('href', productHref);
      const productNameH3 = document.createElement('h3');
      productNameH3.innerHTML = item.product.name;
      productNameA.appendChild(productNameH3);
      itemDiv.appendChild(productNameA);

      const productCategoryA = document.createElement('a');
      productCategoryA.setAttribute('href', productHref);
      const productCategoryH4 = document.createElement('h4');
      productCategoryH4.innerHTML = item.product.category.name;
      productCategoryA.appendChild(productCategoryH4);
      itemDiv.appendChild(productCategoryA);

      const quantityDiv = document.createElement('div');
      quantityDiv.innerHTML = 'Quantity: ' + item.quantity;
      itemDiv.appendChild(quantityDiv);
      
      cartDiv.appendChild(itemDiv);
    });

  }

})();