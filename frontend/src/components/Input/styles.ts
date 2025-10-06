import styled from "styled-components";

export const Field = styled.div`
  display: grid;
  gap: 8px;
`;

export const Label = styled.label`
  font-weight: 600;
  font-size: 14px;
`;

export const InputContainer = styled.input`
  height: 42px;
  border: 1px solid #d1d5db;
  border-radius: 10px;
  padding: 0 12px;
  outline: none;
  font-size: 14px;
  transition: box-shadow 0.15s, border-color 0.15s;

  &:focus {
    border-color: #6366f1;
    box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.15);
  }

  &:disabled {
    background: #f9fafb;
    cursor: not-allowed;
  }
`;

export const Hint = styled.span`
  font-size: 12px;
  color: #9ca3af;
`;
