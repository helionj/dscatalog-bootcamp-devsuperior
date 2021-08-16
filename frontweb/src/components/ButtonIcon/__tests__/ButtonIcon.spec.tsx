import { render, screen } from '@testing-library/react';
import ButtonIcon from '..';

test('ButtonIcon should render button with text given', () => {
  const text = 'Fazer login';

  render(<ButtonIcon text={text} />);

  expect(screen.getByText(text)).toBeInTheDocument();
});
