import React, { useEffect, useState } from "react";

import {
  getSpecialties,
  createSpecialty,
  Specialty,
} from "../../services/specialyService";
import {
  Actions,
  Alert,
  Badge,
  Button,
  Card,
  Empty,
  Form,
  Header,
  List,
  ListItem,
  Page,
  Subtitle,
  Title,
} from "./styles";
import { Input } from "../../components/Input";
import {
  createQueue,
  GetQueue,
  getQueues,
  Queue,
} from "../../services/queueService";

const QueuePage = () => {
  const [items, setItems] = useState<GetQueue[]>([]);
  const [queueDay, setQueueDay] = useState("");
  const [doctorTypeId, setDoctorTypeId] = useState("");
  const [quantityVacancies, setQuantityVacancies] = useState<number>();
  const [loadingList, setLoadingList] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");

  async function loadList() {
    setLoadingList(true);
    setErrorMsg("");
    try {
      const data = await getQueues();

      setItems(data);
    } catch (e) {
      setErrorMsg("Não foi possível carregar as especialidades.");
    } finally {
      setLoadingList(false);
    }
  }

  useEffect(() => {
    loadList();
  }, []);

  async function onSubmit(e: any) {
    e.preventDefault();
    setErrorMsg("");
    setSuccessMsg("");

    const valueQueueDay = queueDay.trim();
    const valueDoctorTypeId = doctorTypeId.trim();
    if (!valueQueueDay || !valueDoctorTypeId || !quantityVacancies) {
      setErrorMsg("Digite o nome da especialidade.");
      return;
    }
    setSubmitting(true);
    try {
      await createQueue({
        queueDay: valueQueueDay,
        doctorTypeId: valueDoctorTypeId,
        quantityVacancies: quantityVacancies,
      });
      setSuccessMsg("Especialidade criada com sucesso!");
      setQueueDay("");
      setDoctorTypeId("");
      setQuantityVacancies(0);
      await loadList();
    } catch (e) {
      setErrorMsg("Falha ao criar a especialidade.");
    } finally {
      setSubmitting(false);
    }
  }

  console.log(items);

  return (
    <Page>
      <Card>
        <Header>
          <Title>Cadastro de Filas</Title>
          <Subtitle>Crie uma nova fila e visualize as existentes</Subtitle>
        </Header>

        <Form onSubmit={onSubmit}>
          <Input
            id="queueDay"
            placeholder="Ex.: 2025-10-13"
            value={queueDay}
            onChange={(e) => setQueueDay(e.target.value)}
            disabled={submitting}
            label="Dia"
          />
          <Input
            id="doctorTypeId"
            placeholder="Ex.: 1"
            value={doctorTypeId}
            onChange={(e) => setDoctorTypeId(e.target.value)}
            disabled={submitting}
            label="Id da Especialidade"
          />
          <Input
            id="quantityVacancies"
            placeholder="Ex.: 20"
            value={quantityVacancies}
            onChange={(e) => setQuantityVacancies(Number(e.target.value))}
            disabled={submitting}
            label="Quantidade"
          />

          <Actions>
            <Button type="submit" disabled={submitting}>
              {submitting ? "Salvando..." : "Criar especialidade"}
            </Button>
          </Actions>

          {errorMsg && (
            <Alert role="alert" $variant="error">
              {errorMsg}
            </Alert>
          )}
          {successMsg && (
            <Alert role="status" $variant="success">
              {successMsg}
            </Alert>
          )}
        </Form>
      </Card>

      <Card>
        <Header>
          <Title>Filas cadastradas</Title>
        </Header>

        {loadingList ? (
          <Empty>Carregando...</Empty>
        ) : items.length === 0 ? (
          <Empty>Nenhuma fila encontrada.</Empty>
        ) : (
          <List>
            {items.map((it) => (
              <ListItem key={it.id}>
                <Badge>
                  <strong>Especialidade:</strong> {it.specialy ?? String(it)}{" "}
                  <strong>Vagas:</strong> {it.quantityVacancies}{" "}
                  <strong>Dia:</strong> {it.queueDay}
                </Badge>
              </ListItem>
            ))}
          </List>
        )}
      </Card>
    </Page>
  );
};

export default QueuePage;
