/**
 * Factory function to create a new measure object.
 * @param {string} userAddress - The blockchain wallet address of the user.
 * @returns {Object} - A new measure object.
 */
export function createMeasure(userAddress = '') {
    return {
        id: null, // can be set by backend or generated with uuid
        userAddress: userAddress,
        description: '',
        photoUrl: '',
        location: '',
        timestamp: new Date().toISOString(),
        status: 'PENDING', // or 'APPROVED' | 'REJECTED'
        supervisorComment: ''
    };
}

/**
 * Simple validation for a measure object.
 * Returns an array of error messages if validation fails.
 * @param {Object} measure
 * @returns {Array<string>} error messages
 */
export function validateMeasure(measure) {
    const errors = [];

    if (!measure.description?.trim()) {
        errors.push('Description is required.');
    }

    if (!measure.photoUrl) {
        errors.push('Photo is required.');
    }

    if (!measure.location?.trim()) {
        errors.push('Location is required.');
    }

    return errors;
}

export function isApproved(measure) {
    return measure.status === 'APPROVED';
}

export function isPending(measure) {
    return measure.status === 'PENDING';
}

export function isRejected(measure) {
    return measure.status === 'REJECTED';
}
