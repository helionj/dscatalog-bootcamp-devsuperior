export const formatPrice = (price:number) => {
    var result = price.toFixed(2).split('.');
    result[0] = result[0].split(/(?=(?:...)*$)/).join('.');
    return result.join(',');
}

/* 
export const formatPrice = (price:number) => {
    const params ={maximumFractionDigits: 2, minimumFractionDigits: 2};
    return new Intl.NumberFormat('pt-BR', params).format(price);
}
*/