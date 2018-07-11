(function() {

  let cartItemCount = 0;

  getItemCount();
  
  function getItemCount() {

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        const cartItems = JSON.parse(xhr.responseText);
        cartItemCount = 0;
        cartItems.forEach(item => cartItemCount += item.quantity)
        render();
      }
    };
    xhr.open('GET', '/cart/items');
    xhr.send();
  }

  function render() {
    const cartItemCountSpan = document.querySelector('#cartItemCount');
    cartItemCountSpan.innerHTML = 'Items: ' + cartItemCount;
  }

})();