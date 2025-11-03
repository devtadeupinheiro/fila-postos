import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Title, InfoBox, ErrorMessage, OptionButton } from './styles';

interface QueueResponse {
  position?: number;
  message?: string;
}

export default function QueueStatus() {
  const [position, setPosition] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchQueueStatus = async () => {
      try {
        const response = await axios.post<QueueResponse>('/schedule-appointment', {
          idQueue: 1, // ← substitua com o ID real da fila
          patientSusNumber: '123456789012345' // ← substitua com o SUS real
        });

        if (response.status === 201 && response.data.position !== undefined) {
          setPosition(response.data.position);
        } else {
          setError(response.data.message || 'Fila cheia ou paciente não entrou.');
        }
      } catch (err) {
        setError('Erro ao consultar a fila.');
      }
    };

    fetchQueueStatus();
  }, []);

  return (
    <Container>
      <Title>Status da sua Fila</Title>

      {error ? (
        <ErrorMessage>{error}</ErrorMessage>
      ) : (
        <InfoBox>
          {position !== null ? (
            <p>Sua posição atual na fila é: <strong>{position}</strong></p>
          ) : (
            <p>⏳ Consultando posição...</p>
          )}
        </InfoBox>
      )}

      <OptionButton onClick={() => navigate('/patient/select-queue')}>
        Voltar
      </OptionButton>
    </Container>
  );
}
