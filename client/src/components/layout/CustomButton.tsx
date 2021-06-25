import React from 'react';

interface CustomButtonProps {
  text: string;
  onClick: React.MouseEventHandler;
  type?: any;
  color: string;
  isBlock?: boolean;
}

const CustomButton = ({
  text,
  onClick,
  type = 'button',
  color = 'primary',
  isBlock = false,
}: CustomButtonProps) => {
  let buttonClass = 'btn';

  // Add button color
  if (color === 'danger') {
    buttonClass += ' btn-danger';
  } else if (color === 'success') {
    buttonClass += ' btn-success';
  } else {
    buttonClass += ' btn-primary';
  }

  // Check if block
  if (isBlock) {
    buttonClass += ' btn-block';
  }

  return (
    <button className={buttonClass} type={type} onClick={onClick}>
      {text}
    </button>
  );
};

export default CustomButton;
