package com.gabor.partypeps.services;

import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.mappers.UserMapper;
import com.gabor.partypeps.models.dao.Follow;
import com.gabor.partypeps.models.dao.User;
import com.gabor.partypeps.models.dto.UserDTO;
import com.gabor.partypeps.repositories.FollowRepository;
import com.gabor.partypeps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService<User, UserDTO> {

    private static UserMapper userMapper = new UserMapper();

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public FollowRepository followRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Transactional
    public List<UserDTO> findAllButNotMe(String myUsername){
        UserDTO myself = this.findMyselfByUsername(myUsername);
        return this.findAll().stream().filter(user -> ! user.name.equals(myUsername)).map(user -> user.setupActions(myself)).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO findUserByUsername(String hisUsername){
        return userMapper.mapToDTO(userRepository.findByUsername(hisUsername));
    }

    @Transactional
    public UserDTO findMyselfByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            return null;
        }
        return userMapper.mapToDTO(user);
    }

    @Transactional
    public UserDTO findUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user == null){
            return null;
        }
        return userMapper.mapToDTO(user);
    }

    @Transactional
    public List<UserDTO> findMyFriends(String username){
        UserDTO myself = this.findMyselfByUsername(username);
        return this.findAll().stream().filter(user -> UserDTO.areFriends(user, myself)).map(him -> him.setupActions(myself)).collect(Collectors.toList());
    }

    /**
     * Function to insert a new User entity into the database
     *
     * @param dto UserDTO
     * @return long the ID of the newly created entity
     */
    @Override
    public long insert(UserDTO dto) {
        UserDTO userDTO = dto;
        userDTO.password = passwordEncoder.encode(userDTO.password);
        List<UserDTO> list = new ArrayList<>();
        list.add(userDTO);
        return userRepository.save(userMapper.mapListOfDAO(list).get(0)).getId();
    }

    /**
     * TODO - Function to update an existing User entity inside the database
     *
     * @param dto UserDTO
     * @return boolean
     */
    @Override
    public boolean update(UserDTO dto) {
        return false;
    }

    @Transactional
    public Boolean followUser(String followerUsername, String followedUsername){
        User followerUser = userRepository.findByUsername(followerUsername);
        User followedUser = userRepository.findByUsername(followedUsername);
        followRepository.save(new Follow(followerUser, followedUser, false));
        return true;
    }

    @Transactional
    public Boolean unfollowUser(String followerUsername, String followedUsername){
        User followingUser = userRepository.findByUsername(followerUsername);
        List<Follow> foundFollow = followingUser.getFollowing().stream().filter(x -> x.getFollowed().getUsername().equals(followedUsername)).collect(Collectors.toList());
        if(! foundFollow.isEmpty()){
            Follow toDeleteFollowFromDB = followRepository.findById(foundFollow.get(0).getId()).get();
            return 1 == followRepository.deleteFollow(toDeleteFollowFromDB.getId());
        }
        return true;
    }

    @Transactional
    public Boolean suicide(String username, Long id){
        User myself = userRepository.findByUsername(username);
        if(myself.getId() == id) {
            return this.delete(id);
        }
        return false;
    }


    @Override
    public JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public AbstractMapper<User, UserDTO> getMapper() {
        return userMapper;
    }

}
