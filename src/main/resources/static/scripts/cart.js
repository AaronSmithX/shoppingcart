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

    let html = '';
    cartItems.forEach(item => {

      html += `
      <div style="padding: 1rem; border: 1px solid black;">
        <h3>${item.product.name}</h3
        <h4>${item.product.category.name}</h4>
        <div>
          <strong>Quantity: </strong> ${item.quantity}
        </div>
      </div>`;
    });

    cartDiv.innerHTML = html;
  }

})();