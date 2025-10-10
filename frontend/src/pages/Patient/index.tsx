import { Container, OptionButton, ButtonGroup } from './styles';
import { useNavigate } from 'react-router-dom';

export default function Patient() {
  const navigate = useNavigate();

  return (
    <Container>
      <h1>Sou Paciente</h1>

      <ButtonGroup>
        <OptionButton onClick={() => navigate('/patient/cadastrar')}>
          Cadastrar
        </OptionButton>

        <OptionButton onClick={() => navigate('/patient/select-queue')}>
          Escolher Fila
        </OptionButton>
      </ButtonGroup>
    </Container>
  );
}
