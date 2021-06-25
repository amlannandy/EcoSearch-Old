import React from 'react';

interface CustomInputProps {
  label: string;
  value: string;
  onChange: React.ChangeEventHandler;
  isBlock?: boolean;
  type?: any;
}

const CustomInput = ({
  label,
  onChange,
  value,
  type = 'text',
  isBlock = false,
}: CustomInputProps) => {
  let inputClass = 'custom-input';
  if (isBlock) {
    inputClass += ' input-block';
  }

  return (
    <div className='input-container'>
      <label className={inputClass}>
        <input
          value={value}
          type={type}
          required
          placeholder=' '
          onChange={onChange}
        />
        <span className='placeholder'>{label}</span>
      </label>
    </div>
  );
};

export default CustomInput;
