import React from 'react';
import {Link, useLoaderData } from 'react-router-dom';
import { useSelector } from 'react-redux'

import axiosCustom from "../../_actions/axiosCustom";
import { logoutCode } from '../../_actions/user_action';
import { landingRenderingLogout } from '../../_actions/user_action' 
import ArtItem from "../../components/commons/artItem";
import { useDispatch } from 'react-redux';
// import { useDispatch, useSelector } from 'react-redux';

export async function loader () {

  const arts = await axiosCustom.get('/arts/all' )
    .then(response=>response.data)
    .catch(error=>console.log(error))
  return {arts};
}

function LandingPage() {
  const dispatch = useDispatch()
  const landingStatus = useSelector(state => state.user.landing_status);
  if (landingStatus) {
    dispatch(landingRenderingLogout())
      .then(
        window.location.reload()
      )
  }
  const handleLogOut = event => {
    event.preventDefault()
    localStorage.removeItem("token")
    localStorage.removeItem("expiration")
    localStorage.removeItem("nickname")
    localStorage.removeItem("profileImg")
    localStorage.removeItem("role")
    localStorage.removeItem("userSeq")
    dispatch(logoutCode())
    // console.log("로그인 했나요?", loginWonder)
  }
  const {arts} = useLoaderData();
  // console.log(arts)

  return (
    <div>
      <button onClick={handleLogOut}>로그아웃</button>
      <div>
        <h1><Link className='link' to="curations">큐레이션🍌</Link></h1>
        <p>현재 진행중인 큐레이션 {`->`} 진행 예정 큐레이션 보여주기</p>
      </div>

      <div>
        <h1><Link className='link' to="commissions">요즘 뜨는 커미션 작가💰</Link></h1>
        <p>이 곳에는 요즘 뜨는 커미션 작가가 들어간다</p>
      </div>

      <div >
        <h1><Link className='link' to="arts">트렌딩🔥</Link></h1>
        <div className="grid__main-components">
          {arts.map((art) =>
            <div key={`art-item_${art.artSeq}`}>
              <ArtItem
                nickname={art.nickname}
                profileImg={art.profileImg}
                userSeq={art.userSeq}
                artThumbnail={art.artThumbnail}
                artName={art.artName}
                artSeq={art.artSeq}
                artHit={art.artHit}
                artLikeCount={art.artLikeCount}
              />
            </div>
          )}
        </div>
      </div>
      
    </div>
  );
}

export default LandingPage
