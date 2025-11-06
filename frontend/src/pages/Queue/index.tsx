import { useEffect, useState } from 'react';
import {
  Container,
  QueueCard,
  Input,
  Button,
  Message,
  Title,
  Section
} from './styles';

import {
  getQueuesWithVacancies,
  insertPatientInQueue,
  type GetQueue
} from '../../services/queueService';

export default function Queue() {
  const [queues, setQueues] = useState<GetQueue[]>([]);
  const [selectedQueueId, setSelectedQueueId] = useState<number | null>(null);
  const [selectedQueueName, setSelectedQueueName] = useState('');
  const [susNumber, setSusNumber] = useState('');
  const [message, setMessage] = useState('');

  // Carrega as filas disponíveis ao entrar na página
  useEffect(() => {
    async function fetchQueues() {
      try {
        const data = await getQueuesWithVacancies();
        setQueues(data);
      } catch {
        setMessage('Erro ao carregar filas disponíveis.');
      }
    }

    fetchQueues();
  }, []);

  // Envia o número do SUS para inserir o paciente na fila
  const handleEnterQueue = async () => {
    try {
      if (!selectedQueueId || !susNumber) {
        setMessage('Preencha todos os campos.');
        return;
      }

      await insertPatientInQueue(selectedQueueId, susNumber);
      setMessage(`Paciente inserido na fila de ${selectedQueueName} com sucesso!`);
      setSelectedQueueId(null);
      setSelectedQueueName('');
      setSusNumber('');
    } catch {
      setMessage('Erro ao inserir paciente na fila.');
    }
  };

  return (
    <Container>
      <Title>Filas disponíveis</Title>

      <Section>
        {queues.map((q) => (
          <QueueCard
            key={q.id}
            onClick={() => {
              setSelectedQueueId(q.id ?? null);
              setSelectedQueueName(q.specialy);
              setMessage('');
            }}
            style={{
              borderColor: selectedQueueId === q.id ? '#007bff' : '#ccc'
            }}
          >
            <p><strong>Especialidade:</strong> {q.specialy}</p>
            <p><strong>Data:</strong> {q.queueDay}</p>
            <p><strong>Vagas:</strong> {q.quantityVacancies}</p>
          </QueueCard>
        ))}
      </Section>

      {selectedQueueId && (
        <>
          <Input
            placeholder="Digite o número do SUS"
            value={susNumber}
            onChange={(e) => setSusNumber(e.target.value)}
          />
          <Button onClick={handleEnterQueue}>Entrar na fila</Button>
        </>
      )}

      {message && <Message>{message}</Message>}
    </Container>
  );
}
