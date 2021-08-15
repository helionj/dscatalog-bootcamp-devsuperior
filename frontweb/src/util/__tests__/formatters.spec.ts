import { doublePrice, formatPrice } from '../formatters';

it('formatPrice should format number pt-BR when given 10.1', () => {
    const result = formatPrice(10.1);
   
    expect(result).toEqual("10.10");
  });

it('doublePrice should return number *2 when given 10.1', () => {

    const result = doublePrice(10.1);
    console.log(formatPrice(result));
    expect(result).toEqual(20.2);
  });
