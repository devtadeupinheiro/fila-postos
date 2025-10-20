import { useState } from 'react';
import {
  Container,
  Title,
  ButtonGroup,
  OptionButton
} from './styles';
import SpecialyList from '../../../components/Input/SpecialyList';

export default function SelectQueue() {
  const [entrouNaFila, setEntrouNaFila] = useState(false);

  return (
    <Container>
      {!entrouNaFila && (
        <>
          <Title>Escolher Fila</Title>
          <ButtonGroup>
            <OptionButton>Consultar sua fila</OptionButton>
            <OptionButton onClick={() => setEntrouNaFila(true)}>
              Escolher fila
            </OptionButton>
          </ButtonGroup>
        </>
      )}

      {entrouNaFila && <SpecialyList />}
    </Container>
  );
}
