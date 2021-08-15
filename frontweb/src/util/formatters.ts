export const formatPrice = (price:number) => {
    const params ={maximumFractionDigits: 2, minimumFractionDigits: 2};
    return new Intl.NumberFormat('pt-BR', params).format(price);
}

export const doublePrice=(price:number) => {
    return price * 2;
}