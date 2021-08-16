import { render, screen } from '@testing-library/react';
import ProductCard from '..';
import { Product } from '../../../types/product';
import { formatPrice } from '../../../util/formatters';

test('ProductCard should render ProductCard', () => {
  const product: Product = {
    id: 1,
    name: 'The Lord of the Rings',
    description:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
    price: 90.5,
    imgUrl:
      'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg',
    date: '2020-07-13T20:50:07.123450Z',
    categories: [
      {
        id: 1,
        name: 'Livros',
      },
    ],
  };

  render(<ProductCard product={product} />);

  screen.debug();

  expect(screen.getByText(product.name)).toBeInTheDocument();
  expect(screen.getByText("R$")).toBeInTheDocument();
  expect(screen.getByText(formatPrice(product.price))).toBeInTheDocument();
  expect(screen.getByAltText("The Lord of the Rings")).toBeInTheDocument();

  
});
