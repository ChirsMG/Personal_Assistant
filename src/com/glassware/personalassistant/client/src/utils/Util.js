export function RandomAlphaNumeric(length){
    var text = [];
  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  for (var i = 0; i < length; i++)
    text.push(possible.charAt(Math.floor(Math.random() * possible.length)));

  return text.join('');
}

function getColumnNum(screenWidth){
  //TODO Confg - column width
  const columnWidth= "300"

  return (Math.floor(screenWidth/columnWidth));
}