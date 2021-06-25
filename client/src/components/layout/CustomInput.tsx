import React from 'react';

interface CustomInputProps {
  label: string;
  isBlock?: boolean;
}

const CustomInput = ({ label, isBlock = false }: CustomInputProps) => {
  let inputClass = 'custom-input';
  if (isBlock) {
    inputClass += ' input-block';
  }

  return (
    <label className={inputClass}>
      <input type='text' placeholder=' ' />
      <span className='placeholder'>{label}</span>
    </label>
  );
};

export default CustomInput;
