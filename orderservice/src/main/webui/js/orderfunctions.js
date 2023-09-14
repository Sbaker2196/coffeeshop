//drink items 
const drinkItems = [
  {id: "espressoButton", name: "espresso", price: "2.50"},
  {id: "americanoButton", name: "americano", price: "3.00"},
  {id: "cappuccinoButton", name: "cappuccino", price: "3.50" },
  {id: "latteButton", name: "latte", price: "3.50"},
  {id: "latteMacchiatoButton", name: "latte macchiato", price: "4.00"}
];

//building order item by traversing the drinkItems collection
  drinkItems.forEach(item => {
    const button = document.getElementById(item.id);
    button.addEventListener("click", () => buyItem(item))
})

//food items 
const foodItems = [
  {id: "bagelButton", name: "bagel", price: "2.50"},
  {id: "croissantButton", name: "croissant", price: "2.00"},
  {id: "sandwichButton", name: "sandwich", price: "6.50"},
  {id: "cheeseCakeButton", name: "cheesecake", price: "5.00"}
];

//building order item by traversing the foodItems collection
foodItems.forEach(item => {
  const button = document.getElementById(item.id);
  button.addEventListener("click", () => buyItem(item));
})

function buyItem(item){
  let order = {
    name: item.name,
    price: item.price,
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
