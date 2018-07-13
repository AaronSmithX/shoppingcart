(function() {

  let cartItemCount = 0;

  getItemCount();
  
  function getItemCount() {

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        cartItemCount = this.responseText;
        render();
      }
    };
    xhr.open('GET', '/cart/itemQuantity');
    xhr.send();
  }

  function render() {
    const cartItemCountSpan = document.querySelector('#cartItemCount');
    cartItemCountSpan.innerHTML = 'Items: ' + cartItemCount;
  }

})();