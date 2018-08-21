package kr.pe.kwonnam.boot.bootreplicationdatasource.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserOuterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInnerService userInnerService;

    /**
     * readOnly = true : read data from readDataSource
     *
     */
    @Transactional(readOnly = true)
    public User findByIdRead(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * readOnly = false : read data from writeDataSource
     *
     */
    @Transactional(readOnly = false)
    public User findByIdWrite(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public void save(User user) throws Exception {
        userRepository.save(user);
    }

    /**
     * Propagation.REQUIRED test
     */
    @Transactional(readOnly = false)
    public Map<String, User> findByIdWriteAndInnerReadWithPropagationRequired(Integer outerFirstId, Integer innerId, Integer outerSecondId) {
        Map<String, User> users = new HashMap<String, User>();
        users.put("outerFirstUser", userRepository.findById(outerFirstId));

        // @Transactional(readOnly = true) but because of propagation = REQUIRED and outerService's @Transaction(readOnly = false)
        // it's transaction is readOnly=false.
        users.put("innerUser", userInnerService.findByUserIdWithPropagationRequired(innerId));
        users.put("outerSecondUser", userRepository.findById(outerSecondId));
        return users;
    }

    /**
     * Propagation.REQUIRES_NEW test
     */
    @Transactional(readOnly = false)
    public Map<String, User> findByIdWriteAndInnerReadWithPropagationRequiresNew(Integer outerFirstId, Integer innerId, Integer outerSecondId) {
        Map<String, User> users = new HashMap<String, User>();
        users.put("outerFirstUser", userRepository.findById(outerFirstId));

        // always readOnly = true because of REQUIRES_NEW
        users.put("innerUser", userInnerService.findByUserIdWithPropagationRequiresNew(innerId));
        users.put("outerSecondUser", userRepository.findById(outerSecondId));
        return users;
    }

    /**
     * Prapagation.MADATORY test
     */
    @Transactional(readOnly = false)
    public Map<String, User> findByIdWriteAndInnerReadWithPoropagationMandatory(Integer outerFirstId, Integer innerId, Integer outerSecondId) {
        Map<String, User> users = new HashMap<String, User>();
        users.put("outerFirstUser", userRepository.findById(outerFirstId));
        users.put("innerUser", userInnerService.findByUserIdWithPropagationMandatory(innerId));
        users.put("outerSecondUser", userRepository.findById(outerSecondId));
        return users;
    }
}
