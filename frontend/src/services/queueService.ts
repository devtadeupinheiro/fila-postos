import { api } from "./api";
import type { AxiosError } from "axios";

export type Queue = {
  doctorTypeId: string;
  queueDay: string;
  quantityVacancies: number;
};
export type GetQueue = {
  id?: number;
  specialy: string;
  queueDay: string;
  quantityVacancies: number;
};

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
