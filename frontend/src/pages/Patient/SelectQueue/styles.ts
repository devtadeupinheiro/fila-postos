import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  gap: 2rem;
  background-color: #f5f5f5;
`;

export const Title = styled.h1`
  font-size: 2rem;
  color: #333;
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: 2rem;
`;

export const OptionButton = styled.button`
  padding: 1rem 2rem;
  font-size: 1.2rem;
  border: none;
  border-radius: 8px;
  background-color: #007bff;
  color: white;
  cursor: pointer;

  &:hover {
    background-color: #0056b3;
  }
`;
