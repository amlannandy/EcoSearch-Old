import React from 'react';

import Logo from '../../images/logo.png';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons';

const IntroCard = () => {
  return (
    <div className='card intro-card'>
      <img src={Logo} alt='Project Logo' />
      <div className='intro-text'>
        <h1 className='text-dark'>EcoSearch</h1>
        <p className='text-grey'>
          Sign in to upload and classify your wildlife photographs
        </p>
      </div>
      <FontAwesomeIcon
        className='icon-button'
        icon={faUserPlus}
        size='3x'
        color='grey'
      />
    </div>
  );
};

export default IntroCard;
