import { api } from "./api";
import type { AxiosError } from "axios";

export type Queue = {
  doctorTypeId: string;
  queueDay: string;
  quantityVacancies: number;
};

export type GetQueue = {
  id?: number;
  specialy: string; // Corrigido de "specialy"
  queueDay: string;
  quantityVacancies: number;
};

// Busca todas as filas
export async function getQueues(): Promise<GetQueue[]> {
  try {
    const { data } = await api.get<GetQueue[]>("/normalQueue");
    return data;
  } catch (err) {
    const e = err as AxiosError;
    console.error("Erro ao buscar filas:", e.response?.data ?? e.message);
    throw e;
  }
}

// Cria uma nova fila
export async function createQueue(newQueue: Queue): Promise<Queue> {
  try {
    const { data } = await api.post<Queue>("/normalQueue", newQueue);
    return data;
  } catch (err) {
    const e = err as AxiosError;
    console.error("Erro ao criar fila:", e.response?.data ?? e.message);
    throw e;
  }
}

// Busca apenas filas com vagas
export async function getQueuesWithVacancies(): Promise<GetQueue[]> {
  try {
    const { data } = await api.get<GetQueue[]>("/normalQueue/withVacancies");
    return data;
  } catch (err) {
    const e = err as AxiosError;
    console.error("Erro ao buscar filas com vagas:", e.response?.data ?? e.message);
    throw e;
  }
}

// Insere paciente na fila
export async function insertPatientInQueue(idQueue: number, patientSusNumber: string): Promise<void> {
  try {
    await api.post("/schedule-appointment", {
      idQueue,
      patientSusNumber
    });
  } catch (err) {
    const e = err as AxiosError;
    console.error("Erro ao inserir paciente na fila:", e.response?.data ?? e.message);
    throw e;
  }
}
