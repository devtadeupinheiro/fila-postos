import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Title,
  Input,
  InfoBox,
  ErrorMessage,
  OptionButton,
  QueueCard
} from './styles';
import { api } from '../../../services/api';

interface QueueItem {
  specialy: string;
  queueDay: string;
  position: number;
}

export default function QueueStatus() {
  const [susNumber, setSusNumber] = useState('');
  const [queues, setQueues] = useState<QueueItem[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const fetchQueueStatus = async () => {
    if (!susNumber.trim()) {
      setError('Digite o número do SUS.');
      return;
    }

    setLoading(true);
    setError(null);
    setQueues([]);

    try {
      const response = await api.get(`/schedule-appointment/${susNumber}`);
      setQueues(response.data);
    } catch {
      setError('Erro ao consultar a fila.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container>
      <Title>Status da sua Fila</Title>

      <Input
        placeholder="Digite seu número do SUS"
        value={susNumber}
        onChange={(e) => setSusNumber(e.target.value)}
      />
      <OptionButton onClick={fetchQueueStatus}>Consultar</OptionButton>

      {error && <ErrorMessage>{error}</ErrorMessage>}
      {loading && <InfoBox>⏳ Consultando posição...</InfoBox>}

      {queues.length > 0 && (
        <InfoBox>
          {queues.map((q, index) => (
            <QueueCard key={index}>
              <p><strong>Especialidade:</strong> {q.specialy}</p>
              <p><strong>Data:</strong> {q.queueDay}</p>
              <p><strong>Posição:</strong> {q.position}</p>
            </QueueCard>
          ))}
        </InfoBox>
      )}

      <OptionButton onClick={() => navigate('/patient/select-queue')}>
        Voltar
      </OptionButton>
    </Container>
  );
}
