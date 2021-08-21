import { render, screen, waitFor } from '@testing-library/react';
import { Router, useParams } from 'react-router-dom';
import Form from '../Form';
import history from '../../../../util/history';
import userEvent from '@testing-library/user-event';
import { server } from './fixtures';
import selectEvent from 'react-select-event';
import { ToastContainer } from 'react-toastify';

beforeAll(() => {
  server.listen();
});
afterEach(() => {
  server.resetHandlers();
});
afterAll(() => {
  server.close();
});

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useParams: jest.fn(),
}));

describe('Product create tests', () => {
  beforeEach(() => {
    (useParams as jest.Mock).mockReturnValue({
      productId: 'create',
    });
  });

  test('should render Form', async () => {
    const text = 'Fazer login';

    render(
      <Router history={history}>
        <ToastContainer />
        <Form />
      </Router>
    );

    const nameInput = screen.getByTestId('name');
    const priceInput = screen.getByTestId('price');
    const imgUrlInput = screen.getByTestId('imgUrl');
    const descriptionInput = screen.getByTestId('description');
    const categoriesInput = screen.getByLabelText('Categorias');
    const submitButton = screen.getByRole('button', { name: /salvar/i });

    await selectEvent.select(categoriesInput, ['Eletrônicos', 'Computadores']);
    userEvent.type(nameInput, 'Computador');
    userEvent.type(priceInput, '1500.10');
    userEvent.type(
      imgUrlInput,
      'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/12-big.jpg'
    );
    userEvent.type(descriptionInput, 'Um computador com processador I5 ');

    userEvent.click(submitButton);

    await waitFor(() => {
      const toastElement = screen.getByText('Produto cadastrado com sucesso');

      expect(toastElement).toBeInTheDocument();
    });
    expect(history.location.pathname).toEqual('/admin/products');
  });
});
