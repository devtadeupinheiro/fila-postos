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

const SpecialyPage = () => {
  const [items, setItems] = useState<Specialty | null>(null);
  const [specialty, setSpecialty] = useState("");
  const [loadingList, setLoadingList] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");

  async function loadList() {
    setLoadingList(true);
    setErrorMsg("");
    try {
      const data = await getSpecialties("dentista");
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

    const value = specialty.trim();
    if (!value) {
      setErrorMsg("Digite o nome da especialidade.");
      return;
    }
    console.log(value);
    setSubmitting(true);
    try {
      await createSpecialty(value);
      setSuccessMsg("Especialidade criada com sucesso!");
      setSpecialty("");
      await loadList();
    } catch (e) {
      setErrorMsg("Falha ao criar a especialidade.");
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <Page>
      <Card>
        <Header>
          <Title>Cadastro de Especialidade</Title>
          <Subtitle>
            Crie uma nova especialidade e visualize as existentes
          </Subtitle>
        </Header>

        <Form onSubmit={onSubmit}>
          <Input
            id="specialty"
            placeholder="Ex.: Dentista"
            value={specialty}
            onChange={(e) => setSpecialty(e.target.value)}
            disabled={submitting}
            label="Especialidade"
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
          <Title>Especialidades cadastradas</Title>
        </Header>

        {/* {loadingList ? (
          <Empty>Carregando...</Empty>
        ) : items.length === 0 ? (
          <Empty>Nenhuma especialidade encontrada.</Empty>
        ) : (
          <List>
            {items.map((it) => (
              <ListItem key={it.id ?? it.specialty}>
                <Badge>{it.specialty ?? String(it)}</Badge>
              </ListItem>
            ))}
          </List>
        )} */}
      </Card>
    </Page>
  );
};

export default SpecialyPage;
