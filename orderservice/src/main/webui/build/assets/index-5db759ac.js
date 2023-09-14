(function polyfill() {
  const relList = document.createElement("link").relList;
  if (relList && relList.supports && relList.supports("modulepreload")) {
    return;
  }
  for (const link of document.querySelectorAll('link[rel="modulepreload"]')) {
    processPreload(link);
  }
  new MutationObserver((mutations) => {
    for (const mutation of mutations) {
      if (mutation.type !== "childList") {
        continue;
      }
      for (const node of mutation.addedNodes) {
        if (node.tagName === "LINK" && node.rel === "modulepreload")
          processPreload(node);
      }
    }
  }).observe(document, { childList: true, subtree: true });
  function getFetchOpts(link) {
    const fetchOpts = {};
    if (link.integrity)
      fetchOpts.integrity = link.integrity;
    if (link.referrerPolicy)
      fetchOpts.referrerPolicy = link.referrerPolicy;
    if (link.crossOrigin === "use-credentials")
      fetchOpts.credentials = "include";
    else if (link.crossOrigin === "anonymous")
      fetchOpts.credentials = "omit";
    else
      fetchOpts.credentials = "same-origin";
    return fetchOpts;
  }
  function processPreload(link) {
    if (link.ep)
      return;
    link.ep = true;
    const fetchOpts = getFetchOpts(link);
    fetch(link.href, fetchOpts);
  }
})();
const quinoa = "";
const espressoButton = document.getElementById("espressoButton");
const americanoButton = document.getElementById("americanoButton");
const cappuccinoButton = document.getElementById("cappuccinoButton");
const latteButton = document.getElementById("latteButton");
const latteMacchiatoButton = document.getElementById("latteMacchiatoButton");
espressoButton.addEventListener("click", buyEspresso);
americanoButton.addEventListener("click", buyAmericano);
cappuccinoButton.addEventListener("click", buyCappuccino);
latteButton.addEventListener("click", buyLatte);
latteMacchiatoButton.addEventListener("click", buyLatteMacchiato);
const bagelButton = document.getElementById("bagelButton");
const croissantButton = document.getElementById("croissantButton");
const sandwichButton = document.getElementById("sandwichButton");
const cheeseCakeButton = document.getElementById("cheeseCakeButton");
bagelButton.addEventListener("click", buyBagel);
croissantButton.addEventListener("click", buyCroissant);
sandwichButton.addEventListener("click", buySandwich);
cheeseCakeButton.addEventListener("click", buyCheeseCake);
function buyEspresso() {
  let order = {
    name: "espresso",
    price: "2.50"
  };
  sendOrder(order);
}
function buyAmericano() {
  let order = {
    name: "americano",
    price: "3.00"
  };
  sendOrder(order);
}
function buyCappuccino() {
  let order = {
    name: "cappuccino",
    price: "3.50"
  };
  sendOrder(order);
}
function buyLatte() {
  let order = {
    name: "latte",
    price: "3.50"
  };
  sendOrder(order);
}
function buyLatteMacchiato() {
  let order = {
    name: "latte macchiato",
    price: "4.00"
  };
  sendOrder(order);
}
function buyBagel() {
  let order = {
    name: "bagel",
    price: "2.50"
  };
  sendOrder(order);
}
function buyCroissant() {
  let order = {
    name: "croissant",
    price: "2.00"
  };
  sendOrder(order);
}
function buySandwich() {
  let order = {
    name: "sandwich",
    price: "6.50"
  };
  sendOrder(order);
}
function buyCheeseCake() {
  let order = {
    name: "cheesecake",
    price: "5.00"
  };
  sendOrder(order);
}
function sendOrder(order) {
  fetch("http://localhost:8080/order-service", {
    method: "POST",
    headers: {
      "Content-type": "application/json"
    },
    body: JSON.stringify(order)
  }).then((response) => response.json()).then((data) => {
    alert(data);
  }).catch((error) => {
    console.error(error);
  });
}
var source = new EventSource(
  "http://localhost:8081/product-service/recipes"
);
source.addEventListener("message", function(event) {
  var eventData = event.data;
  var resultDiv = document.getElementById("eventTextArea");
  resultDiv.innerHTML += eventData + "\n";
});
