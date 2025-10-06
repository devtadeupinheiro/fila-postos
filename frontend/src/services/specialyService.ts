import { api } from "./api";
import type { AxiosError } from "axios";

export type Specialty = { id: string; specialty: string };

export async function getSpecialties(kind = "dentista"): Promise<Specialty> {
  try {
    const { data } = await api.get<Specialty>(
      `/specialy/${encodeURIComponent(kind)}`
    );
    return data;
  } catch (err) {
    const e = err as AxiosError;
    console.error(
      "Erro ao buscar especialidades:",
      e.response?.data ?? e.message
    );
    throw e;
  }
}

export async function createSpecialty(specialy: string): Promise<Specialty> {
  try {
    const { data } = await api.post<Specialty>("/specialy", { specialy });
    return data;
  } catch (err) {
    const e = err as AxiosError;
    console.error(
      "Erro ao criar especialidade:",
      e.response?.data ?? e.message
    );
    throw e;
  }
}
