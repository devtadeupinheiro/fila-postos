import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import {
  Container,
  ButtonGroup,
  OptionButton,
  TitleEscolherFila
} from './styles';

import SpecialyList from '../../../components/Input/SpecialyList';

export default function SelectQueue() {
  const [entrouNaFila, setEntrouNaFila] = useState(false);
  const navigate = useNavigate();

  return (
    <Container>
      {!entrouNaFila ? (
        <>
          <TitleEscolherFila>Escolher Fila</TitleEscolherFila>
          <ButtonGroup>
            <OptionButton onClick={() => navigate('/patient/consultar')}>
              Consultar sua fila
            </OptionButton>
            <OptionButton onClick={() => setEntrouNaFila(true)}>
              Escolher fila
            </OptionButton>
          </ButtonGroup>
        </>
      ) : (
        <>
          <SpecialyList />
          <OptionButton onClick={() => setEntrouNaFila(false)}>
            Voltar
          </OptionButton>
        </>
      )}
    </Container>
  );
}
