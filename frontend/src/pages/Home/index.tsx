import { Container, Title, ButtonGroup, Button } from "./styles";
import { useNavigate } from "react-router-dom";
import { Logo } from "./styles";
import logoImg from "../../assets/logo.png";




function Home() {
  const navigate = useNavigate();

  return (
    <Container>
      <Logo src={logoImg} alt="Logo Fila Postos" />

      <Title>Bem-vindo ao Fila Postos</Title>
      <ButtonGroup>
        <Button onClick={() => navigate("/servidor")}>SOU SERVIDOR</Button>
        <Button onClick={() => navigate("/paciente")}>SOU PACIENTE</Button>
      </ButtonGroup>
    </Container>
  );
}

export default Home;
