import styled from "styled-components";

export const Page = styled.div`
  max-width: 960px;
  margin: 40px auto;
  padding: 0 16px;
  display: grid;
  gap: 24px;
`;

export const Card = styled.section`
  background: #fff;
  border: 1px solid #e9e9ef;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.04);
`;

export const Header = styled.header`
  margin-bottom: 14px;
`;

export const Title = styled.h2`
  margin: 0 0 4px;
  font-size: 20px;
  line-height: 1.2;
`;

export const Subtitle = styled.p`
  margin: 0;
  color: #6b7280;
  font-size: 14px;
`;

export const Form = styled.form`
  display: grid;
  gap: 16px;
`;

export const Actions = styled.div`
  display: flex;
  gap: 12px;
`;

export const Button = styled.button`
  height: 42px;
  padding: 0 16px;
  background: #007bff;
  color: #fff;
  border: 0;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.06s ease, filter 0.15s ease;

  &:hover {
    background: #0056b3;
  }
  &:active {
    transform: translateY(1px);
  }
  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
`;

export const Alert = styled.div<{ $variant: string }>`
  border-radius: 10px;
  padding: 12px 14px;
  font-size: 14px;
  ${({ $variant }) =>
    $variant === "error"
      ? `background:#fef2f2;color:#991b1b;border:1px solid #fecaca;`
      : `background:#ecfeff;color:#075985;border:1px solid #a5f3fc;`}
`;

export const Empty = styled.p`
  margin: 8px 0 0;
  color: #6b7280;
`;

export const List = styled.ul`
  list-style: none;
  margin: 8px 0 0;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 10px;
`;

export const ListItem = styled.li`
  display: flex;
`;

export const Badge = styled.span`
  padding: 8px 10px;
  background: #f3f4f6;
  border: 1px solid #e5e7eb;
  color: #374151;
  font-size: 14px;
  border-radius: 999px;
  width: 100%;
  text-align: center;
`;
