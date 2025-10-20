import { useEffect, useState } from "react";
import axios from "axios";
import {
  FormContainer,
  FormTitle,
  Label,
  Select,
  InputDate,
  SubmitButton
} from "../../pages/Patient/SelectQueue/styles";

interface Especialidade {
  id: number;
  nome: string;
}

export default function SpecialyList() {
  const [especialidades, setEspecialidades] = useState<Especialidade[]>([]);
  const [especialidadeSelecionada, setEspecialidadeSelecionada] = useState<string>("");
  const [data, setData] = useState<string>("");
  const [mensagem, setMensagem] = useState<string>("");

  useEffect(() => {
    async function fetchEspecialidades() {
      try {
        const response = await axios.get("http://localhost:8080/especialidades");
        setEspecialidades(response.data);
      } catch (error) {
        console.error("Erro ao buscar especialidades:", error);
      }
    }

    fetchEspecialidades();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/filas/entrar", {
        especialidadeId: especialidadeSelecionada,
        data,
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
            {esp.nome}
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

      <SubmitButton type="submit">Entrar na fila</SubmitButton>

      {mensagem && <p>{mensagem}</p>}
    </FormContainer>
  );
}
