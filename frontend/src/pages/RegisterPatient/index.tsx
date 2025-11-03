import { useState } from "react";
import axios from "axios";
import {
  Container,
  FormContainer,
  FormTitle,
  Label,
  InputDate,
  SubmitButton,
  Select,
} from "../../pages/Patient/SelectQueue/styles";
import { api } from "../../services/api";

export default function RegisterPatient() {
  const [name, setName] = useState("");
  const [susNumber, setSusNumber] = useState("");
  const [age, setAge] = useState("");
  const [priorityTypeCode, setPriorityTypeCode] = useState("");
  const [mensagem, setMensagem] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await api.post("/register", {
        name,
        susNumber,
        age: Number(age),
        priorityTypeCode: Number(priorityTypeCode),
      });

      setMensagem("Paciente cadastrado com sucesso!");
      setName("");
      setSusNumber("");
      setAge("");
      setPriorityTypeCode("");
    } catch (error) {
      console.error("Erro ao cadastrar paciente:", error);
      setMensagem("Erro ao cadastrar paciente.");
    }
  };

  return (
    <Container>
      <FormContainer onSubmit={handleSubmit}>
        <FormTitle>Cadastrar Paciente</FormTitle>

        <Label>Nome:</Label>
        <InputDate
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
          placeholder="Nome completo"
        />

        <Label>Número do SUS:</Label>
        <InputDate
          type="text"
          value={susNumber}
          onChange={(e) => setSusNumber(e.target.value)}
          required
          placeholder="15 dígitos"
        />

        <Label>Idade:</Label>
        <InputDate
          type="number"
          value={age}
          onChange={(e) => setAge(e.target.value)}
          required
          min={0}
          max={115}
        />

        <Label>Tipo de Prioridade:</Label>
        <Select
          value={priorityTypeCode}
          onChange={(e) => setPriorityTypeCode(e.target.value)}
          required
        >
          <option value="">Selecione</option>
          <option value="0">Sem prioridade</option>
          <option value="1">Gestante</option>
          <option value="2">Criança de colo</option>
          <option value="3">Lactante</option>
          <option value="4">Autista</option>
          <option value="6">Deficiente</option>
          <option value="7">Obeso</option>
          <option value="8">Doador de sangue</option>
        </Select>

        <SubmitButton type="submit">Cadastrar</SubmitButton>

        {mensagem && <p>{mensagem}</p>}
      </FormContainer>
    </Container>
  );
}
