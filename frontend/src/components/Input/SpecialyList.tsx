import { useEffect, useState } from "react";
import axios from "axios";
import {
  FormContainer,
  FormTitle,
  Label,
  Select,
  InputDate,
  SubmitButton,
} from "../../pages/Patient/SelectQueue/styles";
import {
  getQueue,
  getSpecialties,
  NormalQueue,
  Specialty,
} from "../../services/specialyService";
import { api } from "../../services/api";
import { Queue } from "../../services/queueService";

interface Especialidade {
  id: number;
  nome: string;
}

export default function SpecialyList() {
  const [especialidades, setEspecialidades] = useState<NormalQueue[]>([]);
  const [especialidadeSelecionada, setEspecialidadeSelecionada] =
    useState<string>("");
  const [data, setData] = useState<string>("");
  const [susNumber, setSusNumber] = useState<string>("");
  const [mensagem, setMensagem] = useState<string>("");

  async function loadList() {
    try {
      const data = await getQueue();

      setEspecialidades(data);
    } catch (e) {
      console.error("Erro ao buscar especialidades:", e);
    }
  }

  useEffect(() => {
    loadList();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await api.post("/schedule-appointment", {
        idQueue: especialidadeSelecionada,
        // data,
        patientSusNumber: susNumber,
      });

      setMensagem("Paciente entrou na fila com sucesso!");
    } catch (error) {
      console.error("Erro ao entrar na fila:", error);
      setMensagem("Erro ao entrar na fila.");
    }
  };

  return (
    <FormContainer onSubmit={handleSubmit}>
      <FormTitle>Entrar na fila</FormTitle>

      <Label>Especialidade:</Label>
      <Select
        value={especialidadeSelecionada}
        onChange={(e) => setEspecialidadeSelecionada(e.target.value)}
        required
      >
        <option value="">Selecione uma especialidade</option>
        {especialidades.map((esp) => (
          <option key={esp.id} value={esp.id}>
            {esp.specialy}
          </option>
        ))}
      </Select>

      <Label>Data:</Label>
      <InputDate
        type="date"
        value={data}
        onChange={(e) => setData(e.target.value)}
        required
      />
      <InputDate
        type="text"
        value={susNumber}
        onChange={(e) => setSusNumber(e.target.value)}
        required
        placeholder="15 dígitos"
      />

      <SubmitButton type="submit">Entrar na fila</SubmitButton>

      {mensagem && <p>{mensagem}</p>}
    </FormContainer>
  );
}
