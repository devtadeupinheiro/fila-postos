import { Container, Title, ButtonGroup, OptionButton } from './styles';

export default function SelectQueue() {
  return (
    <Container>
      <Title>Escolher Fila</Title>

      <ButtonGroup>
        <OptionButton>Consultar sua fila</OptionButton>
        <OptionButton>Escolher fila</OptionButton>
      </ButtonGroup>
    </Container>
  );
}
