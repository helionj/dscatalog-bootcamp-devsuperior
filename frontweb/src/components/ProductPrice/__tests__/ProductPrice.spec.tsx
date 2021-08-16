
import { render, screen } from '@testing-library/react';
import ProductPrice from '..';

test('ProductPrice should render ProductPrice', () => {
    const price = 1351.0;
    const text = "1.351,00"
    render(<ProductPrice price={price}/>);
  
    screen.debug();

    expect(screen.getByText(text)).toBeInTheDocument();

    expect(screen.getByText("R$")).toBeInTheDocument();

  });