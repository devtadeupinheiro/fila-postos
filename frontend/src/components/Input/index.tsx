import { ComponentProps } from "react";
import { Field, Hint, Label, InputContainer } from "./styles";

interface InputProps extends ComponentProps<"input"> {
  label: string;
}

export function Input({ label, ...rest }: InputProps) {
  return (
    <Field>
      <Label htmlFor="specialty">{label}</Label>
      <InputContainer {...rest} />
      <Hint>Use nomes consistentes (ex.: “Dentista”, “Cardiologista”)</Hint>
    </Field>
  );
}
