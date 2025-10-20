import { Container, OptionButton, ButtonGroup } from "./styles";
import { useNavigate } from "react-router-dom";

export default function Admin() {
  const navigate = useNavigate();

  return (
    <Container>
      <h1>Servidor</h1>

      <ButtonGroup>
        <OptionButton onClick={() => navigate("/specialty")}>
          Especialidade
        </OptionButton>

        <OptionButton onClick={() => navigate("/queue")}>Fila</OptionButton>
      </ButtonGroup>
    </Container>
  );
}
